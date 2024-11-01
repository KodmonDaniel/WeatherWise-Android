package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json

data class Clouds(
    @field:Json(name = "all") val all: Int,
)
