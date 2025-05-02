package com.example.nilpay_registration_android.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object RegistrationForm : Screen("registration_form")
    object Requests : Screen("requests")
}