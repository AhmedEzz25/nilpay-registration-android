package com.example.nilpay_registration_android.presentation.controller.contracts

import com.example.nilpay_registration_android.R
import com.example.nilpay_registration_android.core.presentation.controller.state.TextFieldState

interface LoginContract {
    data class UiState(
        var isLoading: Boolean = false,
        var isPasswordValid: Boolean = false,
        var isUserNameValid: Boolean = false,
        val phoneNumber: TextFieldState = TextFieldState(
            hint = R.string.enter_your_phone_number,
            placeholder = R.string.mobile_number
        ),
        val password: TextFieldState = TextFieldState(
            hint = R.string.enter_your_password,
            placeholder = R.string.password,
        ),
        val errorMessage: String = ""
    )

    sealed interface UiAction {
        data class OnPhoneNumberTextChanged(val phoneNumber: String) : UiAction
        data class OnPasswordTextChanged(val password: String) : UiAction
        data object OnLoginClicked : UiAction
    }

    sealed interface SideEffect {
        data class ShowMessage(val message: String) : SideEffect
        data class ShowMessageFromRes(val resId: Int) : SideEffect
        data class NavigateToLoginVerification(val userName: String, val userId: Int) :
            SideEffect

        data object NavigateToHome : SideEffect
    }
}