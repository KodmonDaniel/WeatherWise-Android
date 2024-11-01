package com.example.weatherwise.utils

import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.data.datasource.CityWeather
import java.util.Date
import java.util.UUID

class WeatherConverter {
    companion object {
        fun convertToRoomWeather(cityWeather: CityWeather): WeatherEntity {
            return WeatherEntity(
                uuid = UUID.randomUUID(),
                city = cityWeather.name,
                desc = cityWeather.weather[0].description,
                icon = cityWeather.weather[0].icon,
                temp = cityWeather.weatherMain.temp,
                humidity = cityWeather.weatherMain.humidity,
                wind = cityWeather.wind.speed,
                clouds = cityWeather.clouds.all,
                date = DateConverter.fromDate(Date())
            )
        }
    }
}