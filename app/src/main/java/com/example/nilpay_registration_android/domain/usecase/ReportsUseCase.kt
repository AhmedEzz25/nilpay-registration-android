package com.example.nilpay_registration_android.domain.usecase

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportsUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(
    ): Flow<BaseResult<List<Report>>> {
        return appRepository.getReports()
    }
}