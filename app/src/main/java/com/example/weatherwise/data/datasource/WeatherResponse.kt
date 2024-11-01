package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json

data class WeatherResponse(
    @field:Json(name = "cnt") val count: Int,
    @field:Json(name = "list") val list: List<CityWeather>
)