package com.example.nilpay_registration_android.domain

interface CustomerRepository {
    suspend fun saveCustomer(customer: Customer): Result<Unit>
    suspend fun submitCustomer(customer: Customer): Result<Unit>
}