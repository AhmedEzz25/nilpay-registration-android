package com.example.nilpay_registration_android.data.datasource.remote

import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginRequest
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT

interface AppApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<WrappedResponse<LoginResponse>>

    @POST("customers")
    suspend fun submitCustomer(@Body customer: Customer): Response<WrappedResponse<String>>

    @PUT("customers/update")
    suspend fun updateCustomer(@Body customer: Customer): Response<WrappedResponse<String>>

    @PUT("customers/update")
    suspend fun changeCustomerStatus(@Body customer: Customer): Response<WrappedResponse<String>>

}