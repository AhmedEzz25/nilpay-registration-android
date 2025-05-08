package com.example.nilpay_registration_android.data.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedErrorResponse
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.data.datasource.remote.AppApi
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginRequest
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
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
    ): Flow<BaseResult<WrappedResponse<LoginResponse>>> {
        return flow {
            val response = api.login(LoginRequest(username, password))
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

    private suspend fun saveToLocalStorage(customer: Customer) {
        // Implementation depends on your DataStore setup
        // This is a simplified example
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey("customer_data")] = Json.encodeToString(customer)
//        }
    }
}