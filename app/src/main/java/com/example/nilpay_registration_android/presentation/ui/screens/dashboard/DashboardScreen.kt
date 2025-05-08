package com.example.nilpay_registration_android.presentation.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nilpay_registration_android.presentation.navigation.Screen

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        )
        {
            DashboardButton(
                title = "Add Customer",
                onClick = { navController.navigate(Screen.AddCustomer.route) },
                modifier = Modifier.weight(1f)

            )
            DashboardButton(
                title = "Saved Customers",
                onClick = { navController.navigate(Screen.SavedRequests.route) },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardButton(
                title = "Reports",
                onClick = {  },
                modifier = Modifier.weight(1f)

            )
            DashboardButton(
                title = "Rejected",
                onClick = {  },
                modifier = Modifier.weight(1f)

            )
        }
    }
}