package com.example.weatherwise.api

import android.util.Log
import com.example.weatherwise.base.Config.Companion.cityList
import com.example.weatherwise.data.datasource.WeatherResponse
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCityWeathers(): WeatherResponse? {
        return try {
            apiService.getWeather(cityList)?.body()
        } catch (e: Exception) {
            Log.d("API_ERROR", e.toString())
            null
        }
    }
}