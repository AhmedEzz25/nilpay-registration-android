package com.example.nilpay_registration_android.data.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.core.data.WrappedErrorResponse
import com.example.nilpay_registration_android.data.datasource.remote.api.AppApi
import com.example.nilpay_registration_android.data.datasource.remote.dto.CreateCustomerDto
import com.example.nilpay_registration_android.data.mapper.toDomain
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.model.UploadFile
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.domain.model.LoginRequest
import com.example.nilpay_registration_android.domain.model.LoginResponse
import com.example.nilpay_registration_android.domain.model.RefreshToken
import com.example.nilpay_registration_android.domain.model.UploadFileRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val tokenManager: TokenManager,
) : AppRepository {

    override suspend fun saveCustomer(customer: Customer): Result<Unit> {
        return try {
            saveToLocalStorage(customer)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitCustomer(customer: Customer): Flow<BaseResult<CreateCustomerDto>> {
        return retryOn401(
            apiCall = { api.submitCustomer(customer) },
            onSuccess = { response -> BaseResult.DataState(response) }
        )
    }

    override suspend fun login(
        username: String,
        password: String,
    ): Flow<BaseResult<LoginResponse>> {
        return retryOn401(
            apiCall = { api.login(LoginRequest(username, password)) },
            onSuccess = { response ->
                val domainResponse = response.toDomain()
                tokenManager.saveAccessToken(domainResponse.accessToken)
                tokenManager.saveRefreshToken(domainResponse.refreshToken)
                BaseResult.DataState(domainResponse)
            }
        )
    }


    override suspend fun uploadFile(
        file: URI,
        uploadFileRequest: UploadFileRequest,
    ): Flow<BaseResult<UploadFile>> {
        val fileObject = File(file.path)
        val requestFile = fileObject.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", fileObject.name, requestFile)

        return retryOn401(
            apiCall = {
                api.uploadImage(
                    file = body,
                    documentType = uploadFileRequest.documentType,
                    customerEmail = uploadFileRequest.customerEmail
                )
            },
            onSuccess = { response ->
                BaseResult.DataState(response.toDomain())
            }
        )
    }


//    override suspend fun getReports(
//    ): Flow<BaseResult<List<Report>>> {
//        return flow {
//            val response = api.getReports(tokenManager.getUserId()!!)
//            if (response.isSuccessful) {
//                val body = response.body()?.data?.map { it.toDomain() } ?: emptyList()
//                emit(BaseResult.DataState(body))
//            } else {
//                val errorBody = response.errorBody()?.charStream()
//                val type = object : TypeToken<WrappedErrorResponse>() {}.type
//                val errorResponse: WrappedErrorResponse =
//                    Gson().fromJson(errorBody, type)
//                emit(BaseResult.ErrorState(errorResponse))
//            }
//        }
//    }

    //    override suspend fun refreshToken(): Boolean {
//        return flow {
//            val response = api.refreshToken()
//            if (response.isSuccessful) {
//                val body = response.body()?.toDomain()
//                emit(BaseResult.DataState(body))
//            } else {
//                val errorBody = response.errorBody()?.charStream()
//                val type = object : TypeToken<WrappedErrorResponse>() {}.type
//                val errorResponse: WrappedErrorResponse =
//                    Gson().fromJson(errorBody, type)
//                emit(BaseResult.ErrorState(errorResponse))
//            }
//        }
//    }
    override suspend fun refreshToken(): Boolean {
        return try {
            val response = api.refreshToken(
                RefreshToken(
                    accessToken = tokenManager.getAccessToken()!!,
                    refreshToken = tokenManager.getRefreshToken()!!
                )
            )
            if (response.isSuccessful) {
                val newToken = response.body()!!.toDomain().accessToken
                val refreshToken = response.body()!!.toDomain().refreshToken
                tokenManager.saveAccessToken(newToken)
                tokenManager.saveRefreshToken(refreshToken)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            println("${e.message} error message")
            false
        }
    }


    override suspend fun getReports(): Flow<BaseResult<List<Report>>> {
        return retryOn401(
            apiCall = { api.getReports(tokenManager.getUserId()!!) },
            onSuccess = { responseBody ->
                val reports = responseBody.toDomain()
                BaseResult.DataState(reports)
            }
        )
    }

    private suspend fun saveToLocalStorage(customer: Customer) {
        // Implementation depends on your DataStore setup
        // This is a simplified example
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey("customer_data")] = Json.encodeToString(customer)
//        }
    }

    private fun <RawT, DomainT> retryOn401(
        apiCall: suspend () -> retrofit2.Response<RawT>,
        onSuccess: (RawT) -> BaseResult<DomainT>,
    ): Flow<BaseResult<DomainT>> {
        return flow {
            var response = apiCall()

            if (response.isSuccessful) {
                emit(onSuccess(response.body()!!))
            } else if (response.code() == 401) {
                val refreshed = refreshToken()
                if (refreshed) {
                    response = apiCall()
                    if (response.isSuccessful) {
                        emit(onSuccess(response.body()!!))
                        return@flow
                    }
                }

                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse = Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse = Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            }
        }
    }
}