package com.example.nilpay_registration_android.presentation.ui.screens.savedrequests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.data.datasource.local.CustomerDao
import com.example.nilpay_registration_android.data.datasource.local.entities.CustomerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedCustomersViewModel @Inject constructor(
    private val customerDao: CustomerDao,
) : ViewModel() {

    private val _customers = MutableStateFlow<List<CustomerEntity>>(emptyList())
    val customers: StateFlow<List<CustomerEntity>> = _customers

    init {
        viewModelScope.launch {
            _customers.value = customerDao.getAllCustomers()
        }
    }
}