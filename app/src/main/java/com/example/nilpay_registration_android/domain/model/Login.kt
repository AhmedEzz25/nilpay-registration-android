package com.example.nilpay_registration_android.domain.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val accessToken: String,
    val userId: String,
    val email: String,
    val refreshToken: String,
    val roles:List<String>
)