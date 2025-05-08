package com.example.nilpay_registration_android.presentation.ui.screens.login

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val userId: String, val email: String)