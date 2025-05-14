package com.example.nilpay_registration_android.presentation.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.presentation.navigation.AppNavHost

@Composable
fun NilpayRegistrationApp(tokenManager: TokenManager) {
    MaterialTheme {
        AppNavHost(tokenManager)
    }
}