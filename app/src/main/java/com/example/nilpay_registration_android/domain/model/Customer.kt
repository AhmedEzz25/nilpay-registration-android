package com.example.nilpay_registration_android.domain.model

import com.example.nilpay_registration_android.data.datasource.remote.dto.FileMetadataDto

data class Customer(
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val mobile: String,
    val email: String,
    val city: String,
    val address: String,
    val isFamily: Boolean,
    val familyMembersCount: Int,
    val other: String? = null,
    val nationalId: String,
    val nationalIdPhoto: FileMetadataDto? = null,
    val personalPhoto: FileMetadataDto? = null,
    val termsFile: FileMetadataDto? = null,
    val qrCodeNumber: String,
    val pin: String,
)