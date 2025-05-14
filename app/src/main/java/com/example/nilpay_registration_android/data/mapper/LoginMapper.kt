package com.example.nilpay_registration_android.data.mapper

import com.example.nilpay_registration_android.data.datasource.remote.dto.LoginDto
import com.example.nilpay_registration_android.domain.model.LoginResponse

fun LoginDto.toDomain(): LoginResponse {
    return LoginResponse(
        token = token,
        email = email,
        userId = userId
    )
}