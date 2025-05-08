package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.data.datasource.local.CustomerDao
import com.example.nilpay_registration_android.data.datasource.local.entities.CustomerEntity
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.usecase.SubmitCustomerUseCase
import com.example.nilpay_registration_android.utils.uploadFileToS3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val customerDao: CustomerDao,
    private val submitCustomerUseCase: SubmitCustomerUseCase,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(CustomerFormState())
    val uiState: StateFlow<CustomerFormState> = _uiState.asStateFlow()
    fun onFieldChange(update: CustomerFormState.() -> CustomerFormState) {
        _uiState.update { it.update() }
    }

    fun validateAndSubmit(): Boolean {
        val state = _uiState.value
        val isValid = with(state) {
            fullName.isNotBlank() && dob.isNotBlank() && gender.isNotBlank() &&
                    mobile.isNotBlank() && city.isNotBlank() && address.isNotBlank() &&
                    (!isFamily || numFamily == 0) && nationalId.isNotBlank() &&
                    personalPhotoPath.isNotBlank() &&
                    nationalIdPhotoPath.isNotBlank() &&
                    qrCodeValue.isNotBlank() &&
                    pin.isNotBlank() && pin == repin
        }
        _uiState.update {
            it.copy(
                errorMessage = if (!isValid) "Please fill all mandatory fields correctly." else null,
                submitted = isValid
            )
        }
        return isValid
    }

    fun submitCustomer() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val personalPhotoUrl = uploadFileToS3(_uiState.value.personalPhotoPath,"personal photo")
            val nationalIdPhotoUrl = uploadFileToS3(_uiState.value.nationalIdPhotoPath,"national id photo")

            submitCustomerUseCase.execute(
                Customer(
                    fullName = _uiState.value.fullName,
                    city = _uiState.value.city,
                    pin = _uiState.value.pin,
                    address = _uiState.value.address,
                    dateOfBirth = _uiState.value.dob,
                    isFamily = _uiState.value.isFamily,
                    email = _uiState.value.email,
                    gender = _uiState.value.gender,
                    other = _uiState.value.other,
                    mobile = _uiState.value.mobile,
                    familyMembersCount = _uiState.value.numFamily,
                    nationalId = _uiState.value.nationalId,
                    qrCodeNumber = _uiState.value.qrCodeValue,
                    nationalIdPhotoPath = _uiState.value.nationalIdPhotoPath,
                    personalPhotoPath = _uiState.value.personalPhotoPath,
                    termsFilePath = _uiState.value.personalPhotoPath
                )
            ).onStart {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                    )
                }
            }
                .collect { result ->
                    when (result) {
                        is BaseResult.DataState -> {
                            if (result.items!!.success) {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = null,
                                        isSubmittedSucceeded = true
                                    )
                                }
                            } else
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.items.message
                                    )
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

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): String {
        val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file.toURI().toString()
    }

    fun saveCustomerLocally() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            val state = uiState.value
            val customer = CustomerEntity(
                fullName = state.fullName,
                dob = state.dob,
                gender = state.gender,
                mobile = state.mobile,
                email = state.email,
                city = state.city,
                address = state.address,
                isFamily = state.isFamily,
                numFamily = state.numFamily,
                other = state.other,
                nationalId = state.nationalId,
                personalPhotoPath = state.personalPhotoPath,
                nationalIdPhotoPath = state.nationalIdPhotoPath,
                qrCodeNumber = state.qrCodeValue,
                pin = state.pin
            )
            customerDao.insertCustomer(customer)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = null,
                    isSavedLocallySucceeded = true
                )
            }
        }
    }
}