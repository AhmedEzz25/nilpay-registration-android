package com.example.nilpay_registration_android.domain.usecase

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(
        username: String,
        password: String,
    ): Flow<BaseResult<WrappedResponse<LoginResponse>>> {
        return appRepository.login(username, password)
    }
}