package com.example.weatherwise.data.di

import android.app.Application
import androidx.room.Room
import com.example.weatherwise.data.dao.AppDatabase
import com.example.weatherwise.data.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun roomDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun weatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.cityWeatherDao()
    }
}