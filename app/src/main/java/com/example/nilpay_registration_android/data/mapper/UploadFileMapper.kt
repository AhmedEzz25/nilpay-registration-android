package com.example.nilpay_registration_android.data.mapper

import com.example.nilpay_registration_android.data.datasource.remote.dto.UploadFileDto
import com.example.nilpay_registration_android.domain.model.UploadFile

fun UploadFileDto.toDomain(): UploadFile {
    return UploadFile(
        key = key,
        contentType = contentType,
        size = size,
        fileName = fileName,
        type = type
    )
}