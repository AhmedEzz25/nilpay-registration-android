//package com.example.nilpay_registration_android.presentation.ui.screens.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract
//import com.example.nilpay_registration_android.presentation.ui.composables.RegistrationFormContent
//
//@Composable
//fun RegistrationForm(
//    state: RegistrationFormContract.UiState,
//    pushEvent: (RegistrationFormContract.UiAction) -> Unit,
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF2A407C),
//                        Color(0xFF31CCBB)
//                    )
//                )
//            )
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .fillMaxHeight(0.95f)
//                .align(Alignment.Center),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFF1A1D32)
//            )
//        ) {
//            RegistrationFormContent(
//                state = RegistrationFormContract.UiState(),
//                pushEvent = pushEvent
//            )
//        }
//
//        if (state.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center),
//                color = Color.White
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RegistrationFormPreview() {
//    MaterialTheme {
//        RegistrationForm(
//            state = RegistrationFormContract.UiState(
//                fullName = "Ahmed ezz",
//                dateOfBirth = "01/01/1990",
//                gender = "Male",
//                mobile = "1234567890",
//                email = "ahmed@example.com",
//                city = "cairo",
//                address = "123 Cairo St",
//                isFamily = true,
//                nationalIdNo = "ABC12345",
//                pin = "123456",
//                rePin = "123456",
//                qrCode = "",
//                qrCodeNo = ""
//            ),
//            pushEvent = {}
//        )
//    }
//}