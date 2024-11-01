package com.example.weatherwise.feature.weather

import com.example.weatherwise.data.datasource.CityWeather

data class WeatherState(
    var cityWeathers: List<CityWeather>,
    var isLoading: Boolean,
    var isSaving: Boolean,
    var showDialog: Boolean,
    var cityWeatherToSave: CityWeather?
)