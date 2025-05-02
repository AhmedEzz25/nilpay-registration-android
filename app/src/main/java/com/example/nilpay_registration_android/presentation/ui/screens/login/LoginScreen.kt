package com.example.nilpay_registration_android.presentation.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.nilpay_registration_android.R
import com.example.nilpay_registration_android.core.presentation.controller.state.TextFieldState
import com.example.nilpay_registration_android.presentation.controller.contracts.LoginContract

@Composable
fun LoginScreen(
    state: LoginContract.UiState,
    pushEvent: (LoginContract.UiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.phoneNumber.textFieldValue,
            onValueChange = { pushEvent(LoginContract.UiAction.OnPhoneNumberTextChanged(it.text)) },
            label = { Text("Mobile Number") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password.textFieldValue,
            onValueChange = { text ->
                pushEvent(LoginContract.UiAction.OnPasswordTextChanged(text.text))
            },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { pushEvent(LoginContract.UiAction.OnLoginClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = if (state.isLoading) "Loading..." else "Login")
        }

        // Optional: show error message
        if (state.errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.errorMessage,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginContract.UiState(
            phoneNumber = TextFieldState(hint = R.string.mobile_number),
            password = TextFieldState(hint = R.string.mobile_number),
            isLoading = false,
        ),
        pushEvent = {}
    )
}
