package com.example.nilpay_registration_android.domain.usecase

import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.WrappedResponse
import com.example.nilpay_registration_android.domain.model.Customer
import com.example.nilpay_registration_android.domain.repository.AppRepository
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitCustomerUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(
        customer: Customer
    ): Flow<BaseResult<WrappedResponse<String>>> {
        return appRepository.submitCustomer(customer)
    }
}