package com.example.weatherwise.feature.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.api.ApiDataSource
import com.example.weatherwise.data.dao.WeatherDao
import com.example.weatherwise.data.datasource.CityWeather
import com.example.weatherwise.utils.WeatherConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val weatherDao: WeatherDao
) : ViewModel() {

    var state by mutableStateOf(
        WeatherState(
            cityWeathers = mutableListOf(),
            isLoading = false,
            isSaving = false,
            showDialog = false,
            cityWeatherToSave = null
        )
    )

    init {
        loadWeathersFromApi()
    }

    fun loadWeathersFromApi() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val resp = apiDataSource.getCityWeathers()
            state = state.copy(
                cityWeathers = resp?.list ?: mutableListOf(),
                isLoading = false
            )
        }
    }

    fun saveRequest(cityWeather: CityWeather) {
        state = state.copy(showDialog = true, cityWeatherToSave = cityWeather)
    }

    fun closeDialog() {
        state = state.copy(showDialog = false)
    }

    fun saveWeather() {
        state = state.copy(isSaving = true, showDialog = false)
        viewModelScope.launch {
            state.cityWeatherToSave?.let { WeatherConverter.convertToRoomWeather(it) }
                ?.let { weatherDao.save(it) }
        }
        state = state.copy(isSaving = false)
    }
}