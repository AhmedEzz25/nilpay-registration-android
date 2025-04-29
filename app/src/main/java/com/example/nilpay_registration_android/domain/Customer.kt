package com.example.nilpay_registration_android.domain

import android.net.Uri

data class Customer(
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val mobileNo: String,
    val email: String,
    val city: String,
    val address: String,
    val hasFamilyMembers: Boolean,
    val nationalIdNo: String,
    val nationalIdPhotoUri: Uri?
)