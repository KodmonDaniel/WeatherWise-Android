package com.example.weatherwise.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityWeatherDao(): WeatherDao
}