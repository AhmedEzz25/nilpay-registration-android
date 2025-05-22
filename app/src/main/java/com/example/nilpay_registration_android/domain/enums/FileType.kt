package com.example.nilpay_registration_android.domain.enums

enum class FileType(val fileType: String) {
    Terms("terms"),
    NationalId("national_id"),
    PersonalPhoto("personal_photo");

    companion object {
        fun fileTypeValue(value: String): FileType? {
            return FileType.entries.find { it.fileType.equals(value, ignoreCase = true) }
        }
    }
}