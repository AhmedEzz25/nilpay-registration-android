package com.example.nilpay_registration_android.data

import androidx.datastore.core.DataStore
import com.example.nilpay_registration_android.domain.Customer
import com.example.nilpay_registration_android.domain.CustomerRepository
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepositoryImpl @Inject constructor(
    private val api: CustomerApi,
    private val dataStore: DataStore<Preferences>
) : CustomerRepository {

    override suspend fun saveCustomer(customer: Customer): Result<Unit> {
        return try {
            // Save to local storage using DataStore
            saveToLocalStorage(customer)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitCustomer(customer: Customer): Result<Unit> {
        return try {
            // Submit to API
            api.submitCustomer(customer)
            // Also save locally
            saveToLocalStorage(customer)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveToLocalStorage(customer: Customer) {
        // Implementation depends on your DataStore setup
        // This is a simplified example
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("customer_data")] = Json.encodeToString(customer)
        }
    }
}