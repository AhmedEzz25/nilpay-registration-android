package com.example.nilpay_registration_android.core.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertToIsoFormat(inputDate: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    val localDate = LocalDate.parse(inputDate, inputFormatter)
    return localDate.atStartOfDay().atOffset(ZoneOffset.UTC).toString()
}