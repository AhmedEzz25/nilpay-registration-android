package com.example.nilpay_registration_android.presentation.ui.screens.savedrequests

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SavedRequestsScreen(viewModel: SavedCustomersViewModel = hiltViewModel()) {
    val customers by viewModel.customers.collectAsState()
    LazyColumn {
        items(customers) { customer ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Name: ${customer.fullName}")
                Text(text = "Mobile: ${customer.mobile}")
                Text(text = "City: ${customer.city}")
                Divider()
            }
        }
    }
}