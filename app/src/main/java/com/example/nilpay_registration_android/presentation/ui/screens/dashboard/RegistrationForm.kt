package com.example.nilpay_registration_android.presentation.ui.screens.dashboard

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nilpay_registration_android.core.presentation.utils.mimeTypeImage
import com.example.nilpay_registration_android.core.presentation.utils.showDatePicker
import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract
import com.example.nilpay_registration_android.presentation.ui.composables.ActionButton
import com.example.nilpay_registration_android.presentation.ui.composables.DropdownFormField
import com.example.nilpay_registration_android.presentation.ui.composables.FileUploadField
import com.example.nilpay_registration_android.presentation.ui.composables.FormField
import com.example.nilpay_registration_android.presentation.ui.composables.RegistrationFormContent
import com.example.nilpay_registration_android.presentation.ui.composables.rememberDocumentPicker

@Composable
fun RegistrationForm(
    state: RegistrationFormContract.UiState,
    pushEvent: (RegistrationFormContract.UiAction) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val genderOptions = listOf("Male", "Female")
    val cityOptions =
        listOf("New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia")

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            pushEvent(RegistrationFormContract.UiAction.UpdatePersonalPhoto(it))
        }
    }
    val documentPicker = rememberDocumentPicker {
        pushEvent(RegistrationFormContract.UiAction.UpdateUploadTerms(it))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A407C),
                        Color(0xFF31CCBB)
                    )
                )
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.95f)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1D32)
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Text(
                        text = "Add New Cust",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Full Name - Text (Mandatory)
                    FormField(
                        label = "Full Name",
                        value = state.fullName,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateFullName(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        errorMessage = state.fieldErrors["fullName"],
                        isMandatory = true
                    )

                    // Date of Birth - Date Picker (Mandatory)
                    FormField(
                        label = "Date Of Birth",
                        value = state.dateOfBirth,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateDateOfBirth(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                // Show date picker dialog
                                context.showDatePicker { date ->
                                    pushEvent(
                                        RegistrationFormContract.UiAction.UpdateDateOfBirth(
                                            date
                                        )
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Date",
                                    tint = Color.DarkGray
                                )
                            }
                        },
                        errorMessage = state.fieldErrors["dateOfBirth"],
                        isMandatory = true
                    )

                    // Gender - Dropdown (Mandatory)
                    DropdownFormField(
                        label = "Gender",
                        value = state.gender,
                        options = genderOptions,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateGender(
                                    it
                                )
                            )
                        },
                        errorMessage = state.fieldErrors["gender"],
                        isMandatory = true
                    )

                    // Mobile - Number (Mandatory)
                    FormField(
                        label = "Mobile",
                        value = state.mobile,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateMobile(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Phone,
                        errorMessage = state.fieldErrors["mobile"],
                        isMandatory = true
                    )

                    // Email - Text
                    FormField(
                        label = "Email",
                        value = state.email,
                        onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateEmail(it)) },
                        keyboardType = KeyboardType.Email,
                        errorMessage = state.fieldErrors["email"],
                        isMandatory = false
                    )

                    // City - Dropdown (Mandatory)
                    DropdownFormField(
                        label = "City",
                        value = state.city,
                        options = cityOptions,
                        onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateCity(it)) },
                        errorMessage = state.fieldErrors["city"],
                        isMandatory = true
                    )

                    // Address - Text (Mandatory)
                    FormField(
                        label = "Address",
                        value = state.address,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateAddress(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    // Show address edit dialog
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.Blue
                                )
                            }
                        },
                        errorMessage = state.fieldErrors["address"],
                        isMandatory = true
                    )

                    // Individual/Family - Checkbox (Mandatory)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = state.isFamily,
                            onCheckedChange = {
                                pushEvent(RegistrationFormContract.UiAction.UpdateIsFamily(it))
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Blue,
                                checkmarkColor = Color.White
                            )
                        )

                        Text(
                            text = "Individual / Family",
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "*",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }

                    // Family Members - Number (Conditional Mandatory)
                    AnimatedVisibility(visible = state.isFamily) {
                        FormField(
                            label = "No. Family Members",
                            value = state.familyMembers,
                            onValueChange = {
                                pushEvent(
                                    RegistrationFormContract.UiAction.UpdateFamilyMembers(
                                        it
                                    )
                                )
                            },
                            keyboardType = KeyboardType.Number,
                            errorMessage = state.fieldErrors["familyMembers"],
                            isMandatory = state.isFamily
                        )
                    }

                    // Other - Text (Optional)
                    FormField(
                        label = "Other",
                        value = state.other,
                        onValueChange = { pushEvent(RegistrationFormContract.UiAction.UpdateOther(it)) },
                        keyboardType = KeyboardType.Text,
                        isMandatory = false
                    )

                    // National ID - Number (Mandatory)
                    FormField(
                        label = "National ID No.",
                        value = state.nationalIdNo,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateNationalIdNo(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Number,
                        errorMessage = state.fieldErrors["nationalIdNo"],
                        isMandatory = true
                    )

                    // National ID Photo - Button (Mandatory)
                    FileUploadField(
                        label = "National ID Photo",
                        value = state.nationalIdPhotoUri?.toString() ?: "",
                        onButtonClick = {
                            // Launch image picker
                            imagePickerLauncher.launch(context.mimeTypeImage()) // ✅ Launch here
                        },
                        errorMessage = state.fieldErrors["nationalIdPhoto"],
                        isMandatory = true
                    )

                    // Personal Photo - Button (Mandatory)
                    FileUploadField(
                        label = "Personal Photo",
                        value = state.personalPhotoUri?.toString() ?: "",
                        onButtonClick = {
                            // Launch image picker
                            imagePickerLauncher.launch(context.mimeTypeImage()) // ✅ Launch here
                        },
                        errorMessage = state.fieldErrors["personalPhoto"],
                        isMandatory = true
                    )

                    // Scan QR Code - Button (Mandatory)
                    FileUploadField(
                        label = "Scan QR Code",
                        value = state.qrCode,
                        buttonText = "Scan",
                        onButtonClick = {
                            // Launch QR scanner
//                        launchQrScanner(context) { result ->
//                            pushEvent(RegistrationFormContract.UiAction.ScanQrCode(result))
//                        }
                        },
                        errorMessage = state.fieldErrors["qrCode"],
                        isMandatory = true
                    )

                    // QR Code No. - Text (Mandatory)
                    FormField(
                        label = "QR Code No.",
                        value = state.qrCodeNo,
                        onValueChange = {
                            pushEvent(
                                RegistrationFormContract.UiAction.UpdateQrCodeNo(
                                    it
                                )
                            )
                        },
                        keyboardType = KeyboardType.Text,
                        errorMessage = state.fieldErrors["qrCodeNo"],
                        isMandatory = true,
                        readOnly = true
                    )

                    // Upload Terms - Button (Mandatory)
                    FileUploadField(
                        label = "Upload Terms",
                        value = state.uploadTermsUri?.toString() ?: "",
                        onButtonClick = {
                            // Launch document picker
                            documentPicker.launch(arrayOf("application/pdf", "image/*"))

                        },
                        errorMessage = state.fieldErrors["uploadTerms"],
                        isMandatory = true
                    )

                    // PIN - Password (Mandatory)
                    FormField(
                        label = "PIN",
                        value = state.pin,
                        onValueChange = {
                            // Only allow 4-6 digit PIN
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                                pushEvent(RegistrationFormContract.UiAction.UpdatePin(it))
                            }
                        },
                        keyboardType = KeyboardType.NumberPassword,
                        visualTransformation = PasswordVisualTransformation(),
                        errorMessage = state.fieldErrors["pin"],
                        isMandatory = true
                    )

                    // Re-PIN - Password (Mandatory)
                    FormField(
                        label = "Re-PIN",
                        value = state.rePin,
                        onValueChange = {
                            // Only allow 4-6 digit PIN
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                                pushEvent(RegistrationFormContract.UiAction.UpdateRePin(it))
                            }
                        },
                        keyboardType = KeyboardType.NumberPassword,
                        visualTransformation = PasswordVisualTransformation(),
                        errorMessage = state.fieldErrors["rePin"],
                        isMandatory = true
                    )

                    // Action Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionButton(
                            text = "Save",
                            onClick = {
                                focusManager.clearFocus()
                                pushEvent(RegistrationFormContract.UiAction.SaveForm)
                            }
                        )

                        ActionButton(
                            text = "Submit",
                            onClick = {
                                focusManager.clearFocus()
                                pushEvent(RegistrationFormContract.UiAction.SubmitForm)
                            }
                        )

                        ActionButton(
                            text = "Cancel",
                            onClick = {
                                focusManager.clearFocus()
                                pushEvent(RegistrationFormContract.UiAction.CancelForm)
                            }
                        )
                    }
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
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
                mobile = "1234567890",
                email = "ahmed@example.com",
                city = "cairo",
                address = "123 Cairo St",
                isFamily = false,
                nationalIdNo = "ABC12345"
            ),
            pushEvent = {}
        )
    }
}