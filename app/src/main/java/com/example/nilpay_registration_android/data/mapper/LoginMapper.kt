package com.example.nilpay_registration_android.data.mapper

import com.example.nilpay_registration_android.data.datasource.remote.dto.LoginDto
import com.example.nilpay_registration_android.domain.model.LoginResponse

fun LoginDto.toDomain(): LoginResponse {
    return LoginResponse(
        accessToken = accessToken,
        email = email,
        userId = userId,
        refreshToken = refreshToken,
        roles = roles
    )
}