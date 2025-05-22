package com.example.nilpay_registration_android.domain.model

data class UploadFile(
    val key: String,
    val fileName: String,
    val contentType: String,
    val size: Int,
    val type: String,
)

data class UploadFileRequest(val documentType: String, val customerEmail: String)
