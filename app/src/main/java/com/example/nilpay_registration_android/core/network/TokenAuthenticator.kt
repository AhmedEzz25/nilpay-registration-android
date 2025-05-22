package com.example.nilpay_registration_android.core.network

import com.example.nilpay_registration_android.core.data.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite retry loops
        if (responseCount(response) >= 2) return null

        val newToken = runBlocking {
            tokenManager.getAccessToken()
        }

        return if (newToken != null) {
            tokenManager.saveAccessToken(newToken)
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null // Give up if token refresh fails
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }
}
