package com.example.nilpay_registration_android.presentation.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppTitleText(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium)

}