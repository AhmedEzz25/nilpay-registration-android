package com.example.nilpay_registration_android.presentation.controller.contracts

import android.net.Uri

object RegistrationFormContract {
    // States for the registration form
    data class UiState(
        val fullName: String = "",
        val dateOfBirth: String = "",
        val gender: String = "",
        val mobileNo: String = "",
        val email: String = "",
        val city: String = "",
        val address: String = "",
        val numberOfFamilyMembers: Boolean = false,
        val nationalIdNo: String = "",
        val nationalIdPhoto: Uri? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false
    )

    // User actions that can be performed
    sealed class UiAction {
        data class UpdateFullName(val fullName: String) : UiAction()
        data class UpdateDateOfBirth(val dateOfBirth: String) : UiAction()
        data class UpdateGender(val gender: String) : UiAction()
        data class UpdateMobileNo(val mobileNo: String) : UiAction()
        data class UpdateEmail(val email: String) : UiAction()
        data class UpdateCity(val city: String) : UiAction()
        data class UpdateAddress(val address: String) : UiAction()
        data class UpdateNumberOfFamilyMembers(val hasFamilyMembers: Boolean) : UiAction()
        data class UpdateNationalIdNo(val nationalIdNo: String) : UiAction()
        data class UpdateNationalIdPhoto(val uri: Uri?) : UiAction()
        object SaveForm : UiAction()
        object SubmitForm : UiAction()
        object CancelForm : UiAction()
    }

    // Events that can be emitted
    sealed class UiEvent {
        object NavigateBack : UiEvent()
        object ShowSuccessMessage : UiEvent()
        data class ShowErrorMessage(val message: String) : UiEvent()
    }
}