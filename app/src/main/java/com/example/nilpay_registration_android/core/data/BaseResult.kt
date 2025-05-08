package com.example.nilpay_registration_android.core.data


sealed class BaseResult<out T> {
    data class DataState<T : Any>(val items: T?) : BaseResult<T>()
    data class ErrorState(val errorResponse: WrappedErrorResponse) : BaseResult<Nothing>()
}