package com.example.nilpay_registration_android.presentation.controller.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.domain.Customer
import com.example.nilpay_registration_android.domain.CustomerRepository
import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFormViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationFormContract.UiState())
    val uiState: StateFlow<RegistrationFormContract.UiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationFormContract.UiEvent>()
    val events = _events.asSharedFlow()

    fun processAction(action: RegistrationFormContract.UiAction) {
        when (action) {
            is RegistrationFormContract.UiAction.UpdateFullName -> {
                _uiState.update { it.copy(fullName = action.fullName) }
            }
            is RegistrationFormContract.UiAction.UpdateDateOfBirth -> {
                _uiState.update { it.copy(dateOfBirth = action.dateOfBirth) }
            }
            is RegistrationFormContract.UiAction.UpdateGender -> {
                _uiState.update { it.copy(gender = action.gender) }
            }
            is RegistrationFormContract.UiAction.UpdateMobileNo -> {
                _uiState.update { it.copy(mobileNo = action.mobileNo) }
            }
            is RegistrationFormContract.UiAction.UpdateEmail -> {
                _uiState.update { it.copy(email = action.email) }
            }
            is RegistrationFormContract.UiAction.UpdateCity -> {
                _uiState.update { it.copy(city = action.city) }
            }
            is RegistrationFormContract.UiAction.UpdateAddress -> {
                _uiState.update { it.copy(address = action.address) }
            }
            is RegistrationFormContract.UiAction.UpdateNumberOfFamilyMembers -> {
                _uiState.update { it.copy(numberOfFamilyMembers = action.hasFamilyMembers) }
            }
            is RegistrationFormContract.UiAction.UpdateNationalIdNo -> {
                _uiState.update { it.copy(nationalIdNo = action.nationalIdNo) }
            }
            is RegistrationFormContract.UiAction.UpdateNationalIdPhoto -> {
                _uiState.update { it.copy(nationalIdPhoto = action.uri) }
            }
            is RegistrationFormContract.UiAction.SaveForm -> {
                saveCustomerData()
            }
            is RegistrationFormContract.UiAction.SubmitForm -> {
                submitCustomerData()
            }
            is RegistrationFormContract.UiAction.CancelForm -> {
                viewModelScope.launch {
                    _events.emit(RegistrationFormContract.UiEvent.NavigateBack)
                }
            }
        }
    }

    private fun saveCustomerData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                customerRepository.saveCustomer(mapStateToCustomer(_uiState.value))
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                _events.emit(RegistrationFormContract.UiEvent.ShowSuccessMessage)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                _events.emit(RegistrationFormContract.UiEvent.ShowErrorMessage(e.message ?: "Unknown error"))
            }
        }
    }

    private fun submitCustomerData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                customerRepository.submitCustomer(mapStateToCustomer(_uiState.value))
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                _events.emit(RegistrationFormContract.UiEvent.ShowSuccessMessage)
                _events.emit(RegistrationFormContract.UiEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                _events.emit(RegistrationFormContract.UiEvent.ShowErrorMessage(e.message ?: "Unknown error"))
            }
        }
    }

    private fun mapStateToCustomer(state: RegistrationFormContract.UiState): Customer {
        return Customer(
            fullName = state.fullName,
            dateOfBirth = state.dateOfBirth,
            gender = state.gender,
            mobileNo = state.mobileNo,
            email = state.email,
            city = state.city,
            address = state.address,
            hasFamilyMembers = state.numberOfFamilyMembers,
            nationalIdNo = state.nationalIdNo,
            nationalIdPhotoUri = state.nationalIdPhoto
        )
    }
}
