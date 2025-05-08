package com.example.nilpay_registration_android.core.data

data class WrappedResponse<T>(
    var success: Boolean,
    var message: String,
    var statusCode: Int,
    var data: T,
)

data class WrappedResponseList<T>(
    var success: Boolean,
    var message: String,
    var statusCode: Int,
    var data: List<T>,
)

data class WrappedErrorResponse(
    var code: String,
    var message: String,
)