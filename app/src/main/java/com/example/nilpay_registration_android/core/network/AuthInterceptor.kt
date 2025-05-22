package com.example.nilpay_registration_android.core.network

import com.example.nilpay_registration_android.core.data.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val request = chain.request()
        val isRefreshRequest = request.url.encodedPath.contains("/refresh", ignoreCase = true)

        if (!isRefreshRequest) {
            val token = runBlocking { tokenManager.getAccessToken() }
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }
//        if (!token.isNullOrEmpty()) {
//            requestBuilder.addHeader("Authorization", "Bearer $token")
//        }

        return chain.proceed(requestBuilder.build())
    }
}