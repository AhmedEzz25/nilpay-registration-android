package com.example.nilpay_registration_android.presentation.ui.screens.savedrequests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.data.datasource.local.CustomerDao
import com.example.nilpay_registration_android.data.datasource.local.entities.CustomerEntity
import com.example.nilpay_registration_android.presentation.ui.screens.reports.all.ReportsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedCustomersViewModel @Inject constructor(
    private val customerDao: CustomerDao,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SavedCustomersUiState())
    val uiState: StateFlow<SavedCustomersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                val customers = customerDao.getAllCustomers()
                it.copy(
                    reports = customers,
                    isEmpty = customers.isEmpty()
                )
            }
        }
    }
}