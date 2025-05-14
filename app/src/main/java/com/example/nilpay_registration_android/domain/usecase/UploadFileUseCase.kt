package com.example.nilpay_registration_android.domain.usecase

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.domain.model.UploadFile
import com.example.nilpay_registration_android.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(
        file: File,
    ): Flow<BaseResult<UploadFile>> {
        return appRepository.uploadFile(file = file.toURI())
    }
}