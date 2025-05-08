package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

data class CustomerFormState(
    val fullName: String = "",
    val dob: String = "",
    val gender: String = "",
    val mobile: String = "",
    val email: String = "",
    val city: String = "",
    val address: String = "",
    val isFamily: Boolean = false,
    val numFamily: Int = 0,
    val other: String = "",
    val nationalId: String = "",
    val personalPhotoPath: String = "",
    val nationalIdPhotoPath: String = "",
    val qrCodeValue: String = "test",
    val pin: String = "",
    val repin: String = "",
    val errorMessage: String? = null,
    val submitted: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null,
    val isSubmittedSucceeded: Boolean = false,
    val isSavedLocallySucceeded: Boolean = false,
)