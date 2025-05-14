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
        nationalIdPhotoPath = nationalIdPhotoPath,
        personalPhotoPath = personalPhotoPath,
        qrCodeNumber = qrCodeNumber,
        termsFilePath = termsFilePath,
        pin = pin,
        createdAt = createdAt,
        createdBy = createdBy,
        updatedAt = updatedAt,
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