package com.example.nilpay_registration_android.core.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

fun Context.showDatePicker(onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate =
                "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    )

    datePickerDialog.show()
}

fun Context.mimeTypeImage(): String = "image/*"
