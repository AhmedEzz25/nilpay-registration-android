package com.example.nilpay_registration_android.data.datasource.remote.dto

data class ReportDto(
    val id: String,
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val mobile: String,
    val email: String,
    val city: String,
    val address: String,
    val isFamily: Boolean,
    val familyMembersCount: Int?,
    val other: String?,
    val nationalId: String,
    val nationalIdPhoto: FileMetadataDto,
    val personalPhoto: FileMetadataDto,
    val qrCodeNumber: String,
    val termsFile: FileMetadataDto,
    val pin: String,
    val createdBy: String,
    val status: String,
    val acceptedAt: String?,
    val acceptedBy: String?,
    val rejectionReasons: List<String>,
    val applicationUserId: String?,
    val createdOnUtc: String,
    val modifiedOnUtc: String?,
    val deleted: Boolean,
    val deletedOnUtc: String?,
    val isPending: Boolean?,
    val isAccepted: Boolean?,
    val isRejected: Boolean?
)

data class FileMetadataDto(
    val key: String,
    val fileName: String,
    val contentType: String,
    val size: Int,
//    val uploadedAt: String,
    val type: String
)
