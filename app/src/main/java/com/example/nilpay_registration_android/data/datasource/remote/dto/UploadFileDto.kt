package com.example.nilpay_registration_android.data.datasource.remote.dto

data class UploadFileDto(
    val key: String,
    val fileName: String,
    val uploadedAt: String,
    val type: String,
    val contentType: String,
    val size: Int,
)