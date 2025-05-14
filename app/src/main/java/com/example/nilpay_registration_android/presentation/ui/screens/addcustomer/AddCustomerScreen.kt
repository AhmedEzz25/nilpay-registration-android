package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nilpay_registration_android.R
import com.example.nilpay_registration_android.presentation.navigation.Screen
import com.example.nilpay_registration_android.presentation.ui.composables.AppTitleText
import com.example.nilpay_registration_android.presentation.ui.composables.CameraCaptureButton
import com.example.nilpay_registration_android.presentation.ui.composables.createDatePickerDialog
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(navController: NavController, onNavigateDashboard: () -> Unit) {
    val context = LocalContext.current
    val viewModel: AddCustomerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(Unit) {
        savedStateHandle?.getLiveData<String>("scannedQr")?.observeForever { qr ->
            viewModel.onFieldChange { copy(qrCodeValue = qr) }
            savedStateHandle.remove<String>("scannedQr")
        }
    }
    val genderOptions = listOf("Male", "Female")
    var genderExpanded by remember { mutableStateOf(false) }
    val datePickerDialog = remember {
        createDatePickerDialog(context) { selectedDate ->
            viewModel.onFieldChange { copy(dob = selectedDate) }
        }
    }


    if (uiState.isSavedLocallySucceeded) {
        Toast.makeText(
            context,
            stringResource(R.string.msg_form_saved_successfully),
            Toast.LENGTH_LONG
        ).show()
        navController.popBackStack()
    }

//    val fromSavedRequests = remember {
//        navController.previousBackStackEntry
//            ?.savedStateHandle
//            ?.get<Boolean>("fromSavedRequests") ?: false
//    }
    val savedCustomerId = remember {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Int>("savedCustomerId")
    }
    LaunchedEffect(Unit) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<String>("prefilledCustomerJson")
            ?.let { json ->
                val customer = Gson().fromJson(json, CustomerFormState::class.java)
                viewModel.onFieldChange {
                    copy(
                        fullName = customer.fullName,
                        dob = customer.dob,
                        gender = customer.gender,
                        mobile = customer.mobile,
                        email = customer.email,
                        city = customer.city,
                        address = customer.address,
                        nationalId = customer.nationalId,
                        isFamily = customer.isFamily,
                        numFamily = customer.numFamily,
                        other = customer.other ?: "",
                        qrCodeValue = customer.qrCodeValue
                    )
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            color = Color.White,
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AppTitleText(stringResource(R.string.label_title_add_customer))
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.fullName,
                    supportingText = {
                        uiState.fullNameError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(fullName = it) } },
                    label = { Text("Full Name*") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    readOnly = true,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.dob,
                    onValueChange = {},
                    label = { Text("Date of Birth*") },
                    supportingText = {
                        uiState.dobError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = { genderExpanded = !genderExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.gender,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender*") },
                        supportingText = {
                            uiState.genderError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false }
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    viewModel.onFieldChange { copy(gender = option) }
                                    genderExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.mobile,
                    supportingText = {
                        uiState.mobileError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(mobile = it) } },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text("Mobile*") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    supportingText = {
                        uiState.emailError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(email = it) } },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    label = { Text("Email") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.city,
                    supportingText = {
                        uiState.cityError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(city = it) } },
                    label = { Text("City*") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.address,
                    supportingText = {
                        uiState.addressError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(address = it) } },
                    label = { Text("Address*") }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = uiState.isFamily,
                        onCheckedChange = { viewModel.onFieldChange { copy(isFamily = it) } }
                    )
                    Text("Family")
                }
                if (uiState.isFamily) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.numFamily.toString(),
                        supportingText = {
                            uiState.familyCountError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        onValueChange = { viewModel.onFieldChange { copy(numFamily = it.toInt()) } },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text("No. of Family Members*") }
                    )
                }
                uiState.other?.let {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = it,
                        onValueChange = { viewModel.onFieldChange { copy(other = it) } },
                        label = { Text("Other") }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.nationalId,
                    supportingText = {
                        uiState.nationalIdError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.onFieldChange { copy(nationalId = it) } },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text("National ID No.*") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Personal Photo

                CameraCaptureButton(
                    label = "Capture Personal Photo*",
                    onBitmapCaptured = {
                        val uri = viewModel.saveBitmapToCache(context, it)
                        viewModel.onFieldChange { copy(personalPhotoFile = uri) }
                    },
                    errorMessage = if (uiState.personalPhotoError != null) "Personal photo is required" else null
                )
                uiState.personalPhotoFile?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = it.toURI().path.toString(),
                        contentDescription = "Personal Photo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ID Photo
                CameraCaptureButton(
                    label = "Pick National ID Photo",
                    onBitmapCaptured = {
                        val uri = viewModel.saveBitmapToCache(context, it)
                        viewModel.onFieldChange { copy(nationalIdPhotoFile = uri) }
                    },
                    errorMessage = if (uiState.nationalIdPhotoError != null) "National ID photo is required" else null
                )

                uiState.nationalIdPhotoFile?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = it.toURI().path.toString(),
                        contentDescription = "National ID Photo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                CameraCaptureButton(
                    label = "Terms Photo*", onBitmapCaptured = {
                        val uri = viewModel.saveBitmapToCache(context, it)
                        viewModel.onFieldChange { copy(termsPhotoFile = uri) }
                    },
                    errorMessage = if (uiState.termsPhotoError != null) "Terms photo is required" else null
                )

                uiState.termsPhotoFile?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = it.toURI().path.toString(),
                        contentDescription = "Terms Photo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // QR Code
                Button(onClick = {
                    navController.navigate(Screen.QrScanner.route)
                }) {
                    Text("Scan QR Code*")
                }
                if (uiState.qrCodeValue.isNotEmpty()) {
                    Text("QR Code: ${uiState.qrCodeValue}")
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.pin,
                    onValueChange = {
                        if (it.length <= 4) {
                            viewModel.onFieldChange { copy(pin = it) }
                        }
                    },
                    supportingText = {
                        uiState.pinError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    label = { Text("PIN*") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.repin,
                    onValueChange = {
                        if (it.length <= 4) {
                            viewModel.onFieldChange { copy(repin = it) }
                        }
                    },
                    supportingText = {
                        uiState.repinError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    label = { Text("Re-PIN*") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                uiState.errorMessage?.let {
                    Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        if (viewModel.validateAndSubmit()) {
                            viewModel.uploadCustomerImagesAndSubmit()
                        }
                    }) {
                        Text("Submit")
                    }

                    Button(onClick = {
                        viewModel.saveCustomerLocally()
                    }) {
                        Text("Save")
                    }

                    Button(onClick = { navController.popBackStack() }) {
                        Text("Cancel")
                    }
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        if (uiState.isSubmittedSucceeded) {
            Toast.makeText(
                context,
                stringResource(R.string.msg_form_submitted_successfully),
                Toast.LENGTH_LONG
            ).show()
            viewModel.deleteCustomerById(savedCustomerId)
            navController.popBackStack()
            onNavigateDashboard()
        }
        if (uiState.error != null) {
            Toast.makeText(context, "${uiState.error} Already applied before", Toast.LENGTH_LONG)
                .show()
        }
    }
}