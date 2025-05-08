package com.example.nilpay_registration_android.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object AddCustomer : Screen("add_customer")
    object SavedRequests: Screen("saved_requests")
    object Reports : Screen("reports")
    object RejectedCustomers : Screen("rejected_customers")
    object QrScanner: Screen("qr_scanner")
}