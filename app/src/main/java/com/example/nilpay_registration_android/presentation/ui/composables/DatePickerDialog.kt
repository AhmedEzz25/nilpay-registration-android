package com.example.nilpay_registration_android.presentation.ui.composables

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun createDatePickerDialog(
    context: Context,
    onDateSelected: (String) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val dob = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(dob)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
