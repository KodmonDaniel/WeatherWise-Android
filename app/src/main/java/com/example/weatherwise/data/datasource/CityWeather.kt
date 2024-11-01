package com.example.weatherwise.data.datasource

import com.squareup.moshi.Json


data class CityWeather(
    @field:Json(name = "weather") val weather: List<Weather>,
    @field:Json(name = "main") val weatherMain: WeatherMain,
    @field:Json(name = "wind") val wind: Wind,
    @field:Json(name = "clouds") val clouds: Clouds,
    @field:Json(name = "name") val name: String,
)
