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
    private val customerRepository: CustomerRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationFormContract.UiState())
    val uiState: StateFlow<RegistrationFormContract.UiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationFormContract.UiEvent>()
    val events = _events.asSharedFlow()

    fun processAction(action: RegistrationFormContract.UiAction) {
        when (action) {
            is RegistrationFormContract.UiAction.UpdateFullName -> {
                _uiState.update { it.copy(fullName = action.fullName) }
                validateField("fullName", action.fullName)
            }

            is RegistrationFormContract.UiAction.UpdateDateOfBirth -> {
                _uiState.update { it.copy(dateOfBirth = action.dateOfBirth) }
                validateField("dateOfBirth", action.dateOfBirth)
            }

            is RegistrationFormContract.UiAction.UpdateGender -> {
                _uiState.update { it.copy(gender = action.gender) }
                validateField("gender", action.gender)
            }

            is RegistrationFormContract.UiAction.UpdateMobile -> {
                _uiState.update { it.copy(mobile = action.mobile) }
                validateField("mobile", action.mobile)
            }

            is RegistrationFormContract.UiAction.UpdateEmail -> {
                _uiState.update { it.copy(email = action.email) }
                validateField("email", action.email)
            }

            is RegistrationFormContract.UiAction.UpdateCity -> {
                _uiState.update { it.copy(city = action.city) }
                validateField("city", action.city)
            }

            is RegistrationFormContract.UiAction.UpdateAddress -> {
                _uiState.update { it.copy(address = action.address) }
                validateField("address", action.address)
            }

            is RegistrationFormContract.UiAction.UpdateIsFamily -> {
                _uiState.update { it.copy(isFamily = action.isFamily) }
                // If unselected, clear family members
                if (!action.isFamily) {
                    _uiState.update { it.copy(familyMembers = "") }
                }
            }

            is RegistrationFormContract.UiAction.UpdateFamilyMembers -> {
                _uiState.update { it.copy(familyMembers = action.familyMembers) }
                if (uiState.value.isFamily) {
                    validateField("familyMembers", action.familyMembers)
                }
            }

            is RegistrationFormContract.UiAction.UpdateOther -> {
                _uiState.update { it.copy(other = action.other) }
            }

            is RegistrationFormContract.UiAction.UpdateNationalIdNo -> {
                _uiState.update { it.copy(nationalIdNo = action.nationalIdNo) }
                validateField("nationalIdNo", action.nationalIdNo)
            }

            is RegistrationFormContract.UiAction.UpdateNationalIdPhoto -> {
                _uiState.update { it.copy(nationalIdPhotoUri = action.uri) }
                validateField("nationalIdPhoto", action.uri?.toString() ?: "")
            }

            is RegistrationFormContract.UiAction.UpdatePersonalPhoto -> {
                _uiState.update { it.copy(personalPhotoUri = action.uri) }
                validateField("personalPhoto", action.uri?.toString() ?: "")
            }

            is RegistrationFormContract.UiAction.ScanQrCode -> {
                _uiState.update { it.copy(qrCode = action.result) }
                // Automatically update QR code number from scan
                _uiState.update { it.copy(qrCodeNo = action.result) }
                validateField("qrCode", action.result)
            }

            is RegistrationFormContract.UiAction.UpdateQrCodeNo -> {
                _uiState.update { it.copy(qrCodeNo = action.qrCodeNo) }
                validateField("qrCodeNo", action.qrCodeNo)
            }

            is RegistrationFormContract.UiAction.UpdateUploadTerms -> {
                _uiState.update { it.copy(uploadTermsUri = action.uri) }
                validateField("uploadTerms", action.uri?.toString() ?: "")
            }

            is RegistrationFormContract.UiAction.UpdatePin -> {
                _uiState.update { it.copy(pin = action.pin) }
                validateField("pin", action.pin)
                validatePinMatch()
            }

            is RegistrationFormContract.UiAction.UpdateRePin -> {
                _uiState.update { it.copy(rePin = action.rePin) }
                validateField("rePin", action.rePin)
                validatePinMatch()
            }

            is RegistrationFormContract.UiAction.SaveForm -> {
                if (validateAllFields()) {
                    saveCustomerData()
                }
            }

            is RegistrationFormContract.UiAction.SubmitForm -> {
                if (validateAllFields()) {
                    submitCustomerData()
                }
            }

            is RegistrationFormContract.UiAction.CancelForm -> {
                viewModelScope.launch {
                    _events.emit(RegistrationFormContract.UiEvent.NavigateBack)
                }
            }

            is RegistrationFormContract.UiAction.UpdateQrCode -> {
            }
        }
    }

    private fun validateField(fieldName: String, value: String) {
        val currentErrors = _uiState.value.fieldErrors.toMutableMap()

        val error = when (fieldName) {
            "fullName" -> if (value.isBlank()) "Full Name is required" else null
            "dateOfBirth" -> if (value.isBlank()) "Date of Birth is required" else null
            "gender" -> if (value.isBlank()) "Gender is required" else null
            "mobile" -> if (value.isBlank()) "Mobile is required" else null
            "city" -> if (value.isBlank()) "City is required" else null
            "address" -> if (value.isBlank()) "Address is required" else null
            "familyMembers" -> {
                if (uiState.value.isFamily && value.isBlank()) "Number of family members is required" else null
            }

            "nationalIdNo" -> if (value.isBlank()) "National ID is required" else null
            "nationalIdPhoto" -> if (value.isBlank()) "National ID Photo is required" else null
            "personalPhoto" -> if (value.isBlank()) "Personal Photo is required" else null
            "qrCode" -> if (value.isBlank()) "QR Code is required" else null
            "qrCodeNo" -> if (value.isBlank()) "QR Code No. is required" else null
            "uploadTerms" -> if (value.isBlank()) "Terms document is required" else null
            "pin" -> {
                when {
                    value.isBlank() -> "PIN is required"
                    value.length < 4 -> "PIN must be at least 4 digits"
                    else -> null
                }
            }

            "rePin" -> {
                when {
                    value.isBlank() -> "Please confirm your PIN"
                    value.length < 4 -> "PIN must be at least 4 digits"
                    else -> null
                }
            }

            "email" -> {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                if (value.isNotBlank() && !value.matches(emailPattern.toRegex()))
                    "Invalid email format"
                else null
            }

            else -> null
        }

        if (error != null) {
            currentErrors[fieldName] = error
        } else {
            currentErrors.remove(fieldName)
        }

        _uiState.update { it.copy(fieldErrors = currentErrors) }
    }

    private fun validatePinMatch() {
        val currentErrors = _uiState.value.fieldErrors.toMutableMap()
        val pin = _uiState.value.pin
        val rePin = _uiState.value.rePin

        if (pin.isNotBlank() && rePin.isNotBlank() && pin != rePin) {
            currentErrors["rePin"] = "PINs do not match"
        } else if (pin.isNotBlank() && rePin.isNotBlank()) {
            currentErrors.remove("rePin")
        }

        _uiState.update { it.copy(fieldErrors = currentErrors) }
    }

    private fun validateAllFields(): Boolean {
        // Validate all mandatory fields
        validateField("fullName", _uiState.value.fullName)
        validateField("dateOfBirth", _uiState.value.dateOfBirth)
        validateField("gender", _uiState.value.gender)
        validateField("mobile", _uiState.value.mobile)
        validateField("email", _uiState.value.email)
        validateField("city", _uiState.value.city)
        validateField("address", _uiState.value.address)

        if (_uiState.value.isFamily) {
            validateField("familyMembers", _uiState.value.familyMembers)
        }

        validateField("nationalIdNo", _uiState.value.nationalIdNo)
        validateField("nationalIdPhoto", _uiState.value.nationalIdPhotoUri?.toString() ?: "")
        validateField("personalPhoto", _uiState.value.personalPhotoUri?.toString() ?: "")
        validateField("qrCode", _uiState.value.qrCode)
        validateField("qrCodeNo", _uiState.value.qrCodeNo)
        validateField("uploadTerms", _uiState.value.uploadTermsUri?.toString() ?: "")
        validateField("pin", _uiState.value.pin)
        validateField("rePin", _uiState.value.rePin)
        validatePinMatch()

        // Check if there are any validation errors
        return _uiState.value.fieldErrors.isEmpty()
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
                _events.emit(
                    RegistrationFormContract.UiEvent.ShowErrorMessage(
                        e.message ?: "Unknown error"
                    )
                )
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
                _events.emit(
                    RegistrationFormContract.UiEvent.ShowErrorMessage(
                        e.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun mapStateToCustomer(state: RegistrationFormContract.UiState): Customer {
        return Customer(
            fullName = state.fullName,
            dateOfBirth = state.dateOfBirth,
            gender = state.gender,
            mobile = state.mobile,
            email = state.email,
            city = state.city,
            address = state.address,
            isFamily = state.isFamily,
            familyMembers = if (state.isFamily) state.familyMembers.toIntOrNull() ?: 0 else 0,
            other = state.other,
            nationalIdNo = state.nationalIdNo,
            nationalIdPhotoUri = state.nationalIdPhotoUri,
            personalPhotoUri = state.personalPhotoUri,
            qrCode = state.qrCode,
            qrCodeNo = state.qrCodeNo,
            uploadTermsUri = state.uploadTermsUri,
            pin = state.pin
        )
    }
}
