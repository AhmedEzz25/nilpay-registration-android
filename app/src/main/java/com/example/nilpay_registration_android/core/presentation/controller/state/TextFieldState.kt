package com.example.nilpay_registration_android.core.presentation.controller.state

import androidx.compose.ui.text.input.TextFieldValue

data class TextFieldState(
    val hint: Int,
    val placeholder: Int? = null,
    var textFieldValue: TextFieldValue = TextFieldValue(text = ""),
    var errorId: Int? = null,
    val length: Int = Int.MAX_VALUE,
    val isEnabled: Boolean = false,
    val isOptional: Boolean = false,
)