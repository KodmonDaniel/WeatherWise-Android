package com.example.weatherwise

import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.utils.DateConverter
import java.util.Date
import java.util.UUID

class MockData {

    val mockDate = Date()

    val mockUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

    val mockWeatherEntity = WeatherEntity(
        uuid = mockUUID,
        city = "NAME",
        desc = "asd",
        icon = "01",
        temp = 1.0,
        humidity = 3,
        wind = 0.0,
        clouds = 100,
        date = DateConverter.fromDate(Date())
    )
}