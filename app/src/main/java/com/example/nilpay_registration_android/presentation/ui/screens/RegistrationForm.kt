package com.example.nilpay_registration_android.presentation.ui.screens

import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract
import com.example.nilpay_registration_android.presentation.controller.vm.RegistrationFormViewModel
import com.example.nilpay_registration_android.presentation.ui.composables.RegistrationFormContent

@Composable
fun RegistrationForm(
    viewModel: RegistrationFormViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Collect UI events
    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is RegistrationFormContract.UiEvent.NavigateBack -> {
                    onNavigateBack()
                }
                is RegistrationFormContract.UiEvent.ShowSuccessMessage -> {
                    Toast.makeText(context, "Customer saved successfully", Toast.LENGTH_SHORT).show()
                }
                is RegistrationFormContract.UiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    RegistrationFormContent(
        state = state,
        pushEvent = { action -> viewModel.processAction(action) }
    )
}
// 6. Compose Preview
@Preview(showBackground = true)
@Composable
fun RegistrationFormPreview() {
    MaterialTheme {
        RegistrationFormContent(
            state = RegistrationFormContract.UiState(
                fullName = "Ahmed ezz",
                dateOfBirth = "01/01/1990",
                gender = "Male",
                mobileNo = "1234567890",
                email = "ahmed@example.com",
                city = "cairo",
                address = "123 Cairo St",
                numberOfFamilyMembers = true,
                nationalIdNo = "ABC12345"
            ),
            pushEvent = {}
        )
    }
}