package com.example.nilpay_registration_android.domain.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val userId: String, val email: String)