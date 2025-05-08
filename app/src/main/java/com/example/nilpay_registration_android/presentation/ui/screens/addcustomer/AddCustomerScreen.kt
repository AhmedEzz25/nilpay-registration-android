package com.example.nilpay_registration_android.presentation.ui.screens.addcustomer

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nilpay_registration_android.presentation.navigation.Screen
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AddCustomerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(Unit) {
        savedStateHandle?.getLiveData<String>("scannedQr")?.observeForever { qr ->
            viewModel.onFieldChange { copy(qrCodeValue = qr) }
            savedStateHandle.remove<String>("scannedQr")
        }
    }
    val idImageLauncher = rememberLauncherForActivityResult(TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = viewModel.saveBitmapToCache(context, it)
            viewModel.onFieldChange { copy(nationalIdPhotoPath = uri) }
        }
    }

    val personalPhotoLauncher = rememberLauncherForActivityResult(TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = viewModel.saveBitmapToCache(context, it)
            viewModel.onFieldChange { copy(personalPhotoPath = uri) }
        }
    }
    val genderOptions = listOf("Male", "Female")
    var genderExpanded by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val dob = "$dayOfMonth/${month + 1}/$year"
                viewModel.onFieldChange { copy(dob = dob) }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    if (uiState.isSubmittedSucceeded) {
        Toast.makeText(context, "Form submitted Successfully", Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }

    if (uiState.isSavedLocallySucceeded) {
        Toast.makeText(context, "Form saved Successfully", Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Add Customer", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.fullName,
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
                onValueChange = { viewModel.onFieldChange { copy(mobile = it) } },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Mobile*") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = { viewModel.onFieldChange { copy(email = it) } },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.city,
                onValueChange = { viewModel.onFieldChange { copy(city = it) } },
                label = { Text("City*") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.address,
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
                    onValueChange = { viewModel.onFieldChange { copy(numFamily = it.toInt()) } },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text("No. of Family Members*") }
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.other,
                onValueChange = { viewModel.onFieldChange { copy(other = it) } },
                label = { Text("Other") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.nationalId,
                onValueChange = { viewModel.onFieldChange { copy(nationalId = it) } },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("National ID No.*") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Personal Photo
            Button(onClick = {
                personalPhotoLauncher.launch(null)
            }) {
                Text("Capture Personal Photo*")
            }
            if (uiState.personalPhotoPath.isNotEmpty()) {
                Text("ID Photo URI: ${uiState.personalPhotoPath}")
            }

            if (uiState.personalPhotoPath.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = uiState.personalPhotoPath,
                    contentDescription = "Personal Photo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ID Photo
            Button(onClick = {
                idImageLauncher.launch(null)
            }) {
                Text("Pick National ID Photo*")
            }
            if (uiState.nationalIdPhotoPath.isNotEmpty()) {
                Text("ID Photo URI: ${uiState.nationalIdPhotoPath}")
            }

            if (uiState.nationalIdPhotoPath.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = uiState.nationalIdPhotoPath,
                    contentDescription = "Personal Photo",
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
                onValueChange = { viewModel.onFieldChange { copy(pin = it) } },
                label = { Text("PIN*") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.repin,
                onValueChange = { viewModel.onFieldChange { copy(repin = it) } },
                label = { Text("Re-PIN*") },
                visualTransformation = PasswordVisualTransformation()
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
                        viewModel.submitCustomer()
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
    }
}