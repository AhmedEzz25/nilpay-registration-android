package com.example.nilpay_registration_android.data

import com.example.nilpay_registration_android.domain.Customer
import com.example.nilpay_registration_android.domain.CustomerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepositoryImpl @Inject constructor(
//    private val api: CustomerApi
) : CustomerRepository {

    override suspend fun saveCustomer(customer: Customer): Result<Unit> {
        return try {
            // Implementation for saving the customer locally
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitCustomer(customer: Customer): Result<Unit> {
        return try {
            // Implementation for submitting the customer to backend
//            api.submitCustomer(customer)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}