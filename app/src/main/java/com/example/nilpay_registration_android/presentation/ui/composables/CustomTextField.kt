package com.example.nilpay_registration_android.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { 
            Text(
                text = placeholder,
                color = Color.Gray
            ) 
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.6f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.8f),
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        readOnly = readOnly,
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp)
    )
}