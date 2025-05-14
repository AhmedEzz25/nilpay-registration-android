package com.example.nilpay_registration_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.presentation.ui.screens.addcustomer.AddCustomerScreen
import com.example.nilpay_registration_android.presentation.ui.screens.addcustomer.QrScannerScreen
import com.example.nilpay_registration_android.presentation.ui.screens.dashboard.DashboardScreen
import com.example.nilpay_registration_android.presentation.ui.screens.dashboard.DashboardViewModel
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginScreen
import com.example.nilpay_registration_android.presentation.ui.screens.login.LoginViewModel
import com.example.nilpay_registration_android.presentation.ui.screens.reports.all.ReportsScreen
import com.example.nilpay_registration_android.presentation.ui.screens.reports.rejected.RejectedCustomersScreen
import com.example.nilpay_registration_android.presentation.ui.screens.savedrequests.SavedCustomersViewModel
import com.example.nilpay_registration_android.presentation.ui.screens.savedrequests.SavedRequestsScreen

@Composable
fun AppNavHost(
    tokenManager: TokenManager,
) {
    val navController = rememberNavController()
    val startDestination = if (tokenManager.getIsLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            LoginScreen(
                uiState = uiState,
                onUsernameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::login,
                isLoggedIn = uiState.isLoggedIn,
                onNavigateDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                })
        }
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(onLogout = viewModel::logout, navController)
        }
        composable(Screen.AddCustomer.route) {
            AddCustomerScreen(
                navController,
                onNavigateDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                })
        }
        composable(Screen.QrScanner.route) {
            QrScannerScreen(navController)
        }
        composable(Screen.SavedRequests.route) {
            val viewModel: SavedCustomersViewModel = hiltViewModel()
            SavedRequestsScreen(viewModel, navController)
        }
        composable(Screen.Reports.route) {
            ReportsScreen()
        }

        composable(Screen.RejectedCustomers.route) {
            RejectedCustomersScreen(navController = navController)
        }
    }
}