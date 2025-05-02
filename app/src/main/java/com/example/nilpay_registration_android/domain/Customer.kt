package com.example.nilpay_registration_android.domain

import android.net.Uri
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Customer(
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val mobile: String,
    val email: String,
    val city: String,
    val address: String,
    val isFamily: Boolean,
    val familyMembers: Int,
    val other: String,
    val nationalIdNo: String,
    @Transient val nationalIdPhotoUri: Uri? = null,
    @Transient val personalPhotoUri: Uri? = null,
    val qrCode: String,
    val qrCodeNo: String,
    @Transient val uploadTermsUri: Uri? = null,
    val pin: String
)