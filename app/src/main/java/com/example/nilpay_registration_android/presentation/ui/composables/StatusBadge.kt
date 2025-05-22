package com.example.nilpay_registration_android.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nilpay_registration_android.domain.enums.Status

@Composable
fun StatusBadge(status: Status) {
    val color = when (status) {
        Status.Accepted -> Color.Green
        Status.Rejected -> Color.Red
        Status.Pending -> Color.Yellow
        Status.Reevaluate -> Color.Gray
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.CenterEnd

    ) {
        Text(
            text = status.apiValue,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .background(color, shape = RoundedCornerShape(8.dp)) // Background only for the text
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
