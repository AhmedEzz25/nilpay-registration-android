package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

import com.example.nilpay_registration_android.data.datasource.remote.dto.FileMetadataDto
import java.io.File

data class CustomerFormState(
    val customerId: Int? = null,
    val fullName: String = "",
    val dob: String = "",
    val gender: String = "",
    val mobile: String = "",
    val email: String = "",
    val city: String = "",
    val address: String = "",
    val isFamily: Boolean = false,
    val numFamily: Int = 0,
    val other: String? = null,
    val nationalId: String = "",
    /*personal photo data*/
    val personalPhotoFile: File? = null,
    val personalPhotoPathURL: FileMetadataDto? = null,
    /*national id photo data*/
    val nationalIdPhotoFile: File? = null,
    val nationalIdPhotoPathURL: FileMetadataDto? = null,
    /*terms photo data*/
    val termsPhotoFile: File? = null,
    val termsPhotoPathURL: FileMetadataDto? = null,
    val qrCodeValue: String = "test",
    val pin: String = "",
    val repin: String = "",
    val errorMessage: String? = null,
    val submitted: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null,

    val isSubmittedSucceeded: Boolean = false,
    val isSavedLocallySucceeded: Boolean = false,

    /*errors */
    val repinError: String? = null,
    val pinError: String? = null,
    val fullNameError: String? = null,
    val dobError: String? = null,
    val emailError: String? = null,
    val mobileError: String? = null,
    val nationalIdError: String? = null,
    val cityError: String? = null,
    val addressError: String? = null,
    val genderError: String? = null,
    val familyCountError: String? = null,
    val personalPhotoError: String? = null,
    val nationalIdPhotoError: String? = null,
    val termsPhotoError: String? = null,
    val qrCodeError: String? = null,
)