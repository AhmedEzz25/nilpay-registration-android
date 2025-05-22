package com.example.nilpay_registration_android.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nilpay_registration_android.domain.model.Report
import com.example.nilpay_registration_android.domain.enums.Status
import com.example.nilpay_registration_android.presentation.navigation.Screen
import com.google.gson.Gson

@Composable
fun ReportCard(clickable: Boolean, navController: NavController?, report: Report) {
    val modifier = if (clickable) {
        Modifier
            .fillMaxWidth()
            .clickable {
                val json = Gson().toJson(report)
                navController?.currentBackStackEntry?.savedStateHandle?.set(
                    "fromRejectedReports",
                    true
                )
                navController?.currentBackStackEntry?.savedStateHandle?.set(
                    "prefilledCustomerJson",
                    json
                )
                navController?.navigate(Screen.AddCustomer.route)
            }
    } else {
        Modifier.fillMaxWidth()
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = report.fullName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "ID: ${report.nationalId}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Phone: ${report.mobile}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Email: ${report.email}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "City: ${report.city}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Address: ${report.address}", style = MaterialTheme.typography.bodyMedium)
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Created by ${report.createdBy} at ${report.createdAt}",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
            Spacer(modifier = Modifier.height(8.dp))
            report.status.let { statusString ->
                Status.fromApiValue(statusString)?.let { status ->
                    StatusBadge(status = status)
                }
            }
        }
    }
}