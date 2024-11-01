package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json

data class Weather(
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String
)