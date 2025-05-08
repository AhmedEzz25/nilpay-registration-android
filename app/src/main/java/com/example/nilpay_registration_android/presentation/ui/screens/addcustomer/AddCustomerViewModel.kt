package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.convertToIsoFormat
import com.example.nilpay_registration_android.data.datasource.local.CustomerDao
import com.example.nilpay_registration_android.data.datasource.local.entities.CustomerEntity
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.usecase.SubmitCustomerUseCase
import com.example.nilpay_registration_android.domain.usecase.UploadFileUseCase
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
    private val uploadFileUseCase: UploadFileUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        CustomerFormState()
    )
    val uiState: StateFlow<CustomerFormState> = _uiState.asStateFlow()
    fun onFieldChange(update: CustomerFormState.() -> CustomerFormState) {
        _uiState.update { it.update() }
    }

    fun validateAndSubmit(): Boolean {
        val state = _uiState.value

        val guidRegex = Regex("^[{(]?[0-9a-fA-F]{8}(-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}[)}]?$")
        val iso8601Regex = Regex("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        val digitsOnlyRegex = Regex("^\\d+$")

        var isValid = true

        val fullNameError = if (state.fullName.isBlank() || state.fullName.length > 100) {
            isValid = false
            "Full name is required and must be at most 100 characters."
        } else null

        val dobError = if (state.dob.isBlank()) {
            isValid = false
            "Date of birth is required."
        } else null

        val emailError =
            if (state.email.isBlank() || state.email.length > 256 || !emailRegex.matches(state.email)) {
                isValid = false
                "Email is required, must be valid and at most 256 characters."
            } else null

        val mobileError =
            if (state.mobile.isBlank() || state.mobile.length > 20 || !digitsOnlyRegex.matches(state.mobile)) {
                isValid = false
                "Mobile is required, must be digits only and at most 20 characters."
            } else null

        val nationalIdError =
            if (state.nationalId.isBlank() || state.nationalId.length != 11 || !digitsOnlyRegex.matches(
                    state.nationalId
                )
            ) {
                isValid = false
                "National ID must be 11 digits."
            } else null

        val cityError = if (state.city.isBlank() || state.city.length > 50) {
            isValid = false
            "City is required and must be at most 50 characters."
        } else null

        val addressError = if (state.address.isBlank() || state.address.length > 500) {
            isValid = false
            "Address is required and must be at most 500 characters."
        } else null

        val pinError = if (state.pin.length != 4 || !digitsOnlyRegex.matches(state.pin)) {
            isValid = false
            "PIN must be exactly 4 digits."
        } else null

        val genderError = if (state.gender !in listOf("Male", "Female")) {
            isValid = false
            "Gender is required (Male or Female)."
        } else null

        val familyCountError = if (state.isFamily && state.numFamily <= 0) {
            isValid = false
            "Family members count is required if 'isFamily' is true."
        } else null

        val personalPhotoError = if (state.personalPhotoFile == null) {
            isValid = false
            "Personal photo is required."
        } else null

        val nationalIdPhotoError = if (state.nationalIdPhotoFile == null) {
            isValid = false
            "National ID photo is required."
        } else null

        val qrCodeError = if (state.qrCodeValue.isBlank()) {
            isValid = false
            "QR code is required."
        } else null

        val repinError = if (state.pin != state.repin) {
            isValid = false
            "PIN and Re-entered PIN must match."
        } else null

        _uiState.update {
            it.copy(
                fullNameError = fullNameError,
                dobError = dobError,
                emailError = emailError,
                mobileError = mobileError,
                nationalIdError = nationalIdError,
                cityError = cityError,
                addressError = addressError,
                pinError = pinError,
                genderError = genderError,
                familyCountError = familyCountError,
                personalPhotoError = personalPhotoError,
                nationalIdPhotoError = nationalIdPhotoError,
                qrCodeError = qrCodeError,
                repinError = repinError,
                errorMessage = if (!isValid) "Please fix the errors above." else null,
                submitted = isValid
            )
        }

        return isValid
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadCustomerImagesAndSubmit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val personalPhotoUrl =
                _uiState.value.personalPhotoFile?.let { uploadFileUseCase.execute(it) }?.onStart {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }?.collect { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            personalPhotoPathURL = result.body()!!.url
                        )
                    }
                }

            val nationalIdPhotoUrl =
                _uiState.value.nationalIdPhotoFile?.let { uploadFileUseCase.execute(it) }?.onStart {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }?.collect { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            nationalIdPhotoPathURL = result.body()!!.url
                        )
                    }
                }


            if (personalPhotoUrl == null || nationalIdPhotoUrl == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false, error = "Failed to upload images. Please try again."
                    )
                }
                return@launch
            }
            submitCustomer()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitCustomer() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            submitCustomerUseCase.execute(
                Customer(
                    fullName = _uiState.value.fullName,
                    city = _uiState.value.city,
                    pin = _uiState.value.pin,
                    address = _uiState.value.address,
                    dateOfBirth = convertToIsoFormat( _uiState.value.dob),
                    isFamily = _uiState.value.isFamily,
                    email = _uiState.value.email,
                    gender = _uiState.value.gender,
                    other = _uiState.value.other,
                    mobile = _uiState.value.mobile,
                    familyMembersCount = _uiState.value.numFamily,
                    nationalId = _uiState.value.nationalId,
                    qrCodeNumber = _uiState.value.qrCodeValue,
                    nationalIdPhotoPath = _uiState.value.nationalIdPhotoPathURL,
                    personalPhotoPath = _uiState.value.personalPhotoPathURL,
                    termsFilePath = _uiState.value.personalPhotoPathURL
                )
            ).onStart {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                    )
                }
            }.collect { result ->
                when (result) {
                    is BaseResult.DataState -> {
                        if (result.items!!.success) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false, error = null, isSubmittedSucceeded = true
                                )
                            }
                        } else _uiState.update {
                            it.copy(
                                isLoading = false, error = result.items.message
                            )
                        }
                    }

                    is BaseResult.ErrorState -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false, error = result.errorResponse.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file
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
                personalPhotoPath = state.personalPhotoPathURL!!,
                nationalIdPhotoPath = state.nationalIdPhotoPathURL!!,
                qrCodeNumber = state.qrCodeValue,
                pin = state.pin
            )
            customerDao.insertCustomer(customer)
            _uiState.update {
                it.copy(
                    isLoading = false, error = null, isSavedLocallySucceeded = true
                )
            }
        }
    }
}