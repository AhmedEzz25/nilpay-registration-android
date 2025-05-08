package com.example.nilpay_registration_android.domain.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.core.data.WrappedResponseList
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.model.ReportsResponse
import com.example.nilpay_registration_android.domain.model.UploadFileResponse
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.net.URI

interface AppRepository {
    suspend fun saveCustomer(customer: Customer): Result<Unit>
    suspend fun submitCustomer(customer: Customer): Flow<BaseResult<WrappedResponse<String>>>
    suspend fun login(
        username: String,
        password: String,
    ): Flow<BaseResult<WrappedResponse<LoginResponse>>>

    suspend fun uploadFile(file: URI): Flow<Response<UploadFileResponse>>
    suspend fun getReports(
    ): Flow<BaseResult<WrappedResponseList<ReportsResponse>>>
}