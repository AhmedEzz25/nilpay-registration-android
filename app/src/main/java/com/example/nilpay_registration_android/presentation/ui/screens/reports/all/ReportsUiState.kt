package com.example.nilpay_registration_android.presentation.ui.screens.reports.all

import com.example.nilpay_registration_android.domain.model.Report

data class ReportsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false,
    val reports: List<Report> = emptyList()
)