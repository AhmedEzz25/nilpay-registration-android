package com.example.nilpay_registration_android.domain.model

enum class Status(val apiValue: String) {
    Rejected("Rejected"),
    Pending("Pending"),
    Accepted("Accepted"),
    Reevaluate("Re-evaluate");

    companion object {
        fun fromApiValue(value: String): Status? {
            return Status.entries.find { it.apiValue.equals(value, ignoreCase = true) }
        }
    }
}
