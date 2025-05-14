package com.example.nilpay_registration_android.presentation.ui.screens.savedrequests

import com.example.nilpay_registration_android.data.datasource.local.entities.CustomerEntity

data class SavedCustomersUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false,
    val reports: List<CustomerEntity> = emptyList()
)