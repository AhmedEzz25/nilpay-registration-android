package com.example.nilpay_registration_android.presentation.controller.contracts

import android.net.Uri

object RegistrationFormContract {
    // States for the registration form
    data class UiState(
        // Basic info
        val fullName: String = "",
        val dateOfBirth: String = "",
        val gender: String = "",
        val mobile: String = "",
        val email: String = "",
        val city: String = "",
        val address: String = "",

        // Additional info
        val isFamily: Boolean = false,
        val familyMembers: String = "",
        val other: String = "",

        // ID information
        val nationalIdNo: String = "",
        val nationalIdPhotoUri: Uri? = null,
        val personalPhotoUri: Uri? = null,

        // QR code info
        val qrCode: String = "",
        val qrCodeNo: String = "",

        // Documents
        val uploadTermsUri: Uri? = null,

        // Security
        val pin: String = "",
        val rePin: String = "",

        // State
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false,

        // Field validation errors
        val fieldErrors: Map<String, String> = emptyMap()
    )

    // User actions that can be performed
    sealed class UiAction {
        data class UpdateFullName(val fullName: String) : UiAction()
        data class UpdateDateOfBirth(val dateOfBirth: String) : UiAction()
        data class UpdateGender(val gender: String) : UiAction()
        data class UpdateMobile(val mobile: String) : UiAction()
        data class UpdateEmail(val email: String) : UiAction()
        data class UpdateCity(val city: String) : UiAction()
        data class UpdateAddress(val address: String) : UiAction()
        data class UpdateIsFamily(val isFamily: Boolean) : UiAction()
        data class UpdateFamilyMembers(val familyMembers: String) : UiAction()
        data class UpdateOther(val other: String) : UiAction()
        data class UpdateNationalIdNo(val nationalIdNo: String) : UiAction()
        data class UpdateNationalIdPhoto(val uri: Uri?) : UiAction()
        data class UpdatePersonalPhoto(val uri: Uri?) : UiAction()
        data class UpdateQrCode(val qrCode: String) : UiAction()
        data class ScanQrCode(val result: String) : UiAction()
        data class UpdateQrCodeNo(val qrCodeNo: String) : UiAction()
        data class UpdateUploadTerms(val uri: Uri?) : UiAction()
        data class UpdatePin(val pin: String) : UiAction()
        data class UpdateRePin(val rePin: String) : UiAction()
        object SaveForm : UiAction()
        object SubmitForm : UiAction()
        object CancelForm : UiAction()
    }

    // Events that can be emitted
    sealed class UiEvent {
        object NavigateBack : UiEvent()
        object ShowSuccessMessage : UiEvent()
        data class ShowErrorMessage(val message: String) : UiEvent()
        object LaunchDatePicker : UiEvent()
        object LaunchIdPhotoSelection : UiEvent()
        object LaunchPersonalPhotoSelection : UiEvent()
        object LaunchQrScanner : UiEvent()
        object LaunchDocumentPicker : UiEvent()
    }
}