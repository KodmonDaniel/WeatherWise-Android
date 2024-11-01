package com.example.weatherwise.api

import com.example.weatherwise.data.datasource.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("group")
    suspend fun getWeather(@Query("id") cityList: String): Response<WeatherResponse>?
}