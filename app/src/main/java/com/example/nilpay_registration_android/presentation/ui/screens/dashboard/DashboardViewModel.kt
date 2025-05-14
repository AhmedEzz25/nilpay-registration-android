package com.example.nilpay_registration_android.presentation.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.example.nilpay_registration_android.core.data.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val tokenManager: TokenManager) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun logout() {
        _uiState.update { it.copy(isLoggedOutClicked = true) }
        tokenManager.clearAll()
    }
}