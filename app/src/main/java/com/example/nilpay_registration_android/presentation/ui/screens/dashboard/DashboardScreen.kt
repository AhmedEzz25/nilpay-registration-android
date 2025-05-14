package com.example.nilpay_registration_android.presentation.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nilpay_registration_android.presentation.navigation.Screen
import com.example.nilpay_registration_android.presentation.ui.composables.DashboardButton

@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    navController: NavController,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
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
                    icon = Icons.Default.PersonAdd,
                    backgroundColor = Color(0xFF4CAF50),
                    onClick = { navController.navigate(Screen.AddCustomer.route) },
                    modifier = Modifier.weight(1f)

                )
                DashboardButton(
                    title = "Saved Customers",
                    icon = Icons.Default.Save,
                    backgroundColor = Color(0xFF2196F3),
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
                    icon = Icons.Default.Assessment,
                    backgroundColor = Color(0xFFFF9800),
                    onClick = { navController.navigate(Screen.Reports.route) },
                    modifier = Modifier.weight(1f)

                )
                DashboardButton(
                    title = "Rejected",
                    onClick = { navController.navigate(Screen.RejectedCustomers.route) },
                    icon = Icons.Default.Block,
                    backgroundColor = Color(0xFFF44336),
                    modifier = Modifier.weight(1f),

                    )
            }
        }
        Button(
            onClick = {
                onLogout()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val context = LocalContext.current
    DashboardScreen(onLogout = {}, navController = NavController(context))
}