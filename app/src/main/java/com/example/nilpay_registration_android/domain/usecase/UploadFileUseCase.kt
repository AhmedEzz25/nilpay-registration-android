package com.example.nilpay_registration_android.domain.usecase

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.model.UploadFileResponse
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(
        file: File,
    ): Flow<Response<UploadFileResponse>>{
        return appRepository.uploadFile(file = file.toURI())
    }
}