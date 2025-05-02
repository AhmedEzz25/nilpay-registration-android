package com.example.nilpay_registration_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nilpay_registration_android.presentation.controller.contracts.LoginContract
import com.example.nilpay_registration_android.presentation.controller.contracts.RegistrationFormContract
import com.example.nilpay_registration_android.presentation.ui.screens.dashboard.DashboardScreen
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginScreen
import com.example.nilpay_registration_android.presentation.ui.screens.dashboard.RegistrationForm
import com.example.nilpay_registration_android.presentation.ui.screens.dashboard.RequestsScreen
import com.example.nilpay_registration_android.presentation.ui.screens.splash.SplashScreen

@Composable
fun RegistrationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                state = LoginContract.UiState(),
                pushEvent = { event ->
                    if (event is LoginContract.UiAction.OnLoginClicked) {
                        navController.navigate(Screen.Dashboard.route)
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToRegistration = {
                    navController.navigate(Screen.RegistrationForm.route)
                },
                onNavigateToRequests = {
                    navController.navigate(Screen.Requests.route)
                }
            )
        }
        composable(Screen.RegistrationForm.route) {
            RegistrationForm(
                state = RegistrationFormContract.UiState(),
                pushEvent = { /* handle registration events */ }
            )
        }
        composable(Screen.Requests.route) {
            RequestsScreen() // Define this screen
        }
    }
}
