package com.example.weatherwise.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date


class DateConverter {
    companion object {
        fun toDateString(dateLong: Long?): String? {
            return dateLong?.let { formatDate(Date(it)) }
        }

        @TypeConverter
        fun fromDate(date: Date?): Long? {
            return date?.time
        }

        @SuppressLint("SimpleDateFormat")
        private fun formatDate(date: Date): String {
            return try {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd")
                dateFormat.format(date)
            } catch (_: Exception) {
                "?"
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun toDateTimeString(date: Long?): String {
            return try {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm")
                dateFormat.format(date)
            } catch (_: Exception) {
                "?"
            }
        }
    }
}