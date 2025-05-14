package com.example.nilpay_registration_android.presentation.ui.screens.savedrequests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nilpay_registration_android.R
import com.example.nilpay_registration_android.presentation.navigation.Screen
import com.example.nilpay_registration_android.presentation.ui.composables.AppTitleText
import com.example.nilpay_registration_android.presentation.ui.composables.EmptyState
import com.google.gson.Gson

@Composable
fun SavedRequestsScreen(
    viewModel: SavedCustomersViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AppTitleText(stringResource(R.string.label_title_saved_requests))
        }
        items(uiState.reports) { customer ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val json = Gson().toJson(customer)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "fromSavedRequests",
                            true
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "savedCustomerId",
                            customer.id
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "prefilledCustomerJson",
                            json,

                            )
                        navController.navigate(Screen.AddCustomer.route)
                    },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = customer.fullName,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ID: ${customer.nationalId}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Phone: ${customer.mobile}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Email: ${customer.email}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "City: ${customer.city}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Address: ${customer.address}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
    if (uiState.isEmpty)
        EmptyState()
}
