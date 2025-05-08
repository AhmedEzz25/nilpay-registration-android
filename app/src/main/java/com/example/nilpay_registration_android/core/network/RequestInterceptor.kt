package com.example.nilpay_registration_android.core.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val contentType = request.body?.contentType()

        val newRequestBuilder = request.newBuilder()
        if (contentType?.subtype == "json") {
            newRequestBuilder.addHeader("Accept", "application/json")
            newRequestBuilder.addHeader("Content-Type", "application/json")
        }
        return chain.proceed(newRequestBuilder.build())
    }
}