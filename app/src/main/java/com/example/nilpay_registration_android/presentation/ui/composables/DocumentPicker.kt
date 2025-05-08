package com.example.nilpay_registration_android.presentation.ui.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberDocumentPicker(onDocumentPicked: (Uri) -> Unit) =
    rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let { onDocumentPicked(it) }
    }