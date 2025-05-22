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
        private const val REFRESH_TOKEN_KEY = "auth_refresh_token"
        private const val USER_ID_KEY = "auth_user_id"
        private const val USER_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit { putString(REFRESH_TOKEN_KEY, refreshToken) }
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit { putString(USER_ID_KEY, userId) }

    }

    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID_KEY, null)
    }

    fun getIsLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(USER_IS_LOGGED_IN, false)
    }

    fun setLogIn() {
        sharedPreferences.edit { putBoolean(USER_IS_LOGGED_IN, true) }
    }

    fun setLogout() {
        sharedPreferences.edit { putBoolean(USER_IS_LOGGED_IN, false) }
    }

    fun clearAll() {
        sharedPreferences.edit {
            remove(TOKEN_KEY)
            remove(USER_ID_KEY)
            remove(REFRESH_TOKEN_KEY)
            putBoolean(USER_IS_LOGGED_IN, false)
        }
    }
}
