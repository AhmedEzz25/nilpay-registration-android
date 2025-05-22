package com.example.nilpay_registration_android.data.mapper

import com.example.nilpay_registration_android.data.datasource.remote.dto.ReportDto
import com.example.nilpay_registration_android.domain.model.Report

fun ReportDto.toDomain(): Report {
    return Report(
        id = id,
        fullName = fullName,
        dateOfBirth = dateOfBirth,
        gender = gender,
        mobile = mobile,
        email = email,
        city = city,
        address = address,
        isFamily = isFamily,
        familyMembersCount = familyMembersCount,
        other = other,
        nationalId = nationalId,
        nationalIdPhotoPath = nationalIdPhoto.key,
        personalPhotoPath = personalPhoto.key,
        qrCodeNumber = qrCodeNumber,
        termsFilePath = termsFile.key,
        pin = pin,
        createdAt = createdOnUtc,
        createdBy = createdBy,
        updatedAt = modifiedOnUtc,
        status = status,
        acceptedAt = acceptedAt,
        acceptedBy = acceptedBy,
        rejectionReasons = rejectionReasons,
        applicationUserId = applicationUserId,
        isPending = isPending,
        isAccepted = isAccepted,
        isRejected = isRejected
    )
}

fun List<ReportDto>.toDomain(): List<Report> {
    return this.map { it.toDomain() }
}