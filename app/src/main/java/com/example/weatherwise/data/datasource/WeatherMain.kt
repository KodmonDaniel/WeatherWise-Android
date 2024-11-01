package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json

data class WeatherMain(
    @field:Json(name = "temp") val temp: Double,
    @field:Json(name = "pressure") val pressure: Int,
    @field:Json(name = "humidity") val humidity: Int,
)

