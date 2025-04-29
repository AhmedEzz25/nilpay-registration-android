package com.example.nilpay_registration_android.presentation.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.nilpay_registration_android.presentation.ui.screens.RegistrationApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RegistrationApp()
            }
        }
    }
}