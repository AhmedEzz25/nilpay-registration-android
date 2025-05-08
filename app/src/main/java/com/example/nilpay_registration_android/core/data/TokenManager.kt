package com.example.nilpay_registration_android.core.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit


class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}
