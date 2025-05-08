package com.example.nilpay_registration_android.domain.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

interface AppRepository {
    suspend fun saveCustomer(customer: Customer): Result<Unit>
    suspend fun submitCustomer(customer: Customer): Flow<BaseResult<WrappedResponse<String>>>
    suspend fun login(username: String, password: String): Flow<BaseResult<WrappedResponse<LoginResponse>>>
}