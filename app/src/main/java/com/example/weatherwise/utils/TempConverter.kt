package com.example.weatherwise.utils

class TempConverter {

    companion object {
        fun celsiusFromKelvin(kelvin: Double?): Int {
            return (kelvin?.minus(272.15))?.toInt() ?: 0
        }

        // more converters, fahrenheit, etc. if needed
    }
}