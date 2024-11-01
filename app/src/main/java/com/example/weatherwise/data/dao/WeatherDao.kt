package com.example.weatherwise.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(weather: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity")
    suspend fun getWeathers(): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity WHERE uuid = :uuid")
    suspend fun deleteWeather(uuid: UUID)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clear()
}