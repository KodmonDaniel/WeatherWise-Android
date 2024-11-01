package com.example.weatherwise.feature.history

import com.example.weatherwise.data.dao.WeatherEntity

data class HistoryState(
    val weatherEntities: List<WeatherEntity>,
    val isLoading: Boolean,
    val showDialog: Boolean
)