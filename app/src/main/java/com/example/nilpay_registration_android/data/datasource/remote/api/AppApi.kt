package com.example.nilpay_registration_android.data.datasource.remote.api

import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.core.data.WrappedResponseList
import com.example.nilpay_registration_android.data.datasource.remote.dto.CreateCustomerDto
import com.example.nilpay_registration_android.data.datasource.remote.dto.LoginDto
import com.example.nilpay_registration_android.data.datasource.remote.dto.RefreshTokenDto
import com.example.nilpay_registration_android.data.datasource.remote.dto.ReportDto
import com.example.nilpay_registration_android.data.datasource.remote.dto.UploadFileDto
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.model.LoginRequest
import com.example.nilpay_registration_android.domain.model.RefreshToken
import com.example.nilpay_registration_android.domain.model.UploadFileRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface AppApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginDto>

    @POST("customers")
    suspend fun submitCustomer(@Body customer: Customer): Response<CreateCustomerDto>

    @PUT("customers/update")
    suspend fun updateCustomer(@Body customer: Customer): Response<String>

    @PUT("customers/update")
    suspend fun changeCustomerStatus(@Body customer: Customer): Response<String>

    @Multipart
    @POST("files")
    suspend fun uploadImage(
        @Query("documentType") documentType: String,
        @Query("customerEmail") customerEmail: String,
        @Part file: MultipartBody.Part,
    ): Response<UploadFileDto>

    @GET("customers")
    suspend fun getReports(
//        @Query("status") status: String,
        @Query("createdBy") createdBy: String,
    ): Response<List<ReportDto>>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshToken): Response<RefreshTokenDto>
}

