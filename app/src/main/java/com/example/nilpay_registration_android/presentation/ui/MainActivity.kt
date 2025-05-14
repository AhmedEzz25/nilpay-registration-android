package com.example.nilpay_registration_android.presentation.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.presentation.ui.screens.NilpayRegistrationApp
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NilpayRegistrationApp(tokenManager)
            }
        }
    }
}