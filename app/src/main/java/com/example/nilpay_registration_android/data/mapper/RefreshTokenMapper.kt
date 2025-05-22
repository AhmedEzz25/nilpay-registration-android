package com.example.nilpay_registration_android.data.mapper

import com.example.nilpay_registration_android.data.datasource.remote.dto.RefreshTokenDto
import com.example.nilpay_registration_android.domain.model.RefreshToken

fun RefreshTokenDto.toDomain(): RefreshToken {
    return RefreshToken(
        refreshToken = refreshToken,
        accessToken = accessToken
    )
}