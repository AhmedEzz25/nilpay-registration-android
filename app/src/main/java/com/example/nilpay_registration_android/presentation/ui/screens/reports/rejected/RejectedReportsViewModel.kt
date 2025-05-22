package com.example.nilpay_registration_android.presentation.ui.screens.reports.rejected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.enums.Status
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
class RejectedReportsViewModel @Inject constructor(
    reportsUseCase: ReportsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RejectedReportsUiState())
    val uiState: StateFlow<RejectedReportsUiState> = _uiState.asStateFlow()

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
                                items.filter { it.status == Status.Rejected.name }
                                    .takeIf { it.isNotEmpty() }
                                    ?.let { filtered ->
                                        _uiState.update { it.copy(reports = filtered) }
                                    } ?: _uiState.update {
                                    it.copy(
                                        reports = emptyList(),
                                        isEmpty = true
                                    )
                                }
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