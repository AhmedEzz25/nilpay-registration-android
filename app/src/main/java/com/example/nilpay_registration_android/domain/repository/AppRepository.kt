package com.example.nilpay_registration_android.domain.repository

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.model.UploadFile
import com.example.nilpay_registration_android.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import java.net.URI

interface AppRepository {
    suspend fun saveCustomer(customer: Customer): Result<Unit>
    suspend fun submitCustomer(customer: Customer): Flow<BaseResult<WrappedResponse<String>>>
    suspend fun login(
        username: String,
        password: String,
    ): Flow<BaseResult<LoginResponse>>

    suspend fun uploadFile(file: URI): Flow<BaseResult<UploadFile>>
    suspend fun getReports(): Flow<BaseResult<List<Report>>>

//    suspend fun getRejectedReports(status: String): Flow<BaseResult<WrappedResponse<ReportsResponse>>>
}