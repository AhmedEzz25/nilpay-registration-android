package com.example.nilpay_registration_android.presentation.ui.screens.reports.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.domain.usecase.ReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    reportsUseCase: ReportsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reportsUseCase.execute().onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.collect { result ->
                when (result) {
                    is BaseResult.DataState -> {
                        _uiState.update { it.copy(isLoading = false) }
                        result.items?.let { items ->
                            if (items.isNotEmpty()) {
                                _uiState.update { it.copy(reports = items) }
                            } else
                                _uiState.update { it.copy(isEmpty = true) }
                        }
                    }

                    is BaseResult.ErrorState -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.errorResponse.message
                            )
                        }
                    }
                }
            }
        }
    }
}