package com.example.nilpay_registration_android.data.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.core.data.WrappedErrorResponse
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.data.datasource.remote.api.AppApi
import com.example.nilpay_registration_android.data.mapper.toDomain
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.model.UploadFile
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.domain.model.LoginRequest
import com.example.nilpay_registration_android.domain.model.LoginResponse
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

    override suspend fun submitCustomer(customer: Customer): Flow<BaseResult<WrappedResponse<String>>> {
        return flow {
            val response = api.submitCustomer(customer)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            }
        }
    }

    override suspend fun login(
        username: String,
        password: String,
    ): Flow<BaseResult<LoginResponse>> {
        return flow {
            val response = api.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                if (response.body()!!.success) {
                    val body = response.body()!!.data.toDomain()
                    emit(BaseResult.DataState(body))
                }
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            }
        }
    }

    override suspend fun uploadFile(file: URI): Flow<BaseResult<UploadFile>> {
        return flow {
            val fileObject = File(file.path)
            val requestFile = fileObject.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", fileObject.name, requestFile)

            val response = api.uploadImage(body)
            if (response.isSuccessful) {
                val uploadFile = response.body()!!.toDomain()
                emit(BaseResult.DataState(uploadFile))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse = Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            }
        }
    }

    override suspend fun getReports(
    ): Flow<BaseResult<List<Report>>> {
        return flow {
            val response = api.getReports(tokenManager.getUserId()!!)
            if (response.isSuccessful) {
                val body = response.body()?.data?.map { it.toDomain() } ?: emptyList()
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse))
            }
        }
    }

    private suspend fun saveToLocalStorage(customer: Customer) {
        // Implementation depends on your DataStore setup
        // This is a simplified example
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey("customer_data")] = Json.encodeToString(customer)
//        }
    }


}