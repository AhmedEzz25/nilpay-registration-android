package com.example.nilpay_registration_android.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nilpay_registration_android.data.datasource.remote.dto.FileMetadataDto

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val dob: String,
    val gender: String,
    val mobile: String,
    val email: String?,
    val city: String,
    val address: String,
    val isFamily: Boolean,
    val numFamily: Int?,
    val other: String?,
    val nationalId: String,
//    val personalPhotoPath: FileMetadataDto?,
//    val nationalIdPhotoPath: FileMetadataDto?,
//    val termsPhotoPath: FileMetadataDto?,
    val qrCodeNumber: String,
    val pin: String,
)