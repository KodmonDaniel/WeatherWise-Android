package com.example.weatherwise.feature.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.data.dao.WeatherDao
import com.example.weatherwise.data.dao.WeatherEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val weatherDao: WeatherDao) : ViewModel() {

    var state by mutableStateOf(
        DetailsState(
            isLoading = false,
            showDialog = false
        )
    )

    fun toggleDialog(show: Boolean) {
        state = state.copy(showDialog = show)
    }

    fun save(weatherEntity: WeatherEntity) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {

            weatherDao.save(weatherEntity)
            state = state.copy(isLoading = false)
        }
    }

    fun delete(id: UUID) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            weatherDao.deleteWeather(id)
            state = state.copy(isLoading = false)
        }
    }
}