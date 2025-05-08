package com.example.nilpay_registration_android.domain.model

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
    val nationalIdPhotoPath: String? = null,
    val personalPhotoPath: String? = null,
    val qrCodeNumber: String,
    val termsFilePath: String? = null,
    val pin: String,
)