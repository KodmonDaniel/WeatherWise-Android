package com.example.weatherwise.data.di

import com.example.weatherwise.api.ApiDataSource
import com.example.weatherwise.data.dao.WeatherDao
import com.example.weatherwise.feature.history.HistoryViewModel
import com.example.weatherwise.feature.weather.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelModule {

    @Singleton
    @Provides
    fun weatherViewModel(apiDataSource: ApiDataSource, weatherDao: WeatherDao): WeatherViewModel {
        return WeatherViewModel(apiDataSource, weatherDao)
    }

    @Singleton
    @Provides
    fun historyViewModel(weatherDao: WeatherDao): HistoryViewModel {
        return HistoryViewModel(weatherDao)
    }
}