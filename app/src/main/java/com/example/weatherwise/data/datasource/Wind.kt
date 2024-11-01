package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json

data class Wind(
    @field:Json(name = "speed") val speed: Double,
)

