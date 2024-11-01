package com.example.weatherwise.feature.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.data.dao.WeatherDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val weatherDao: WeatherDao) : ViewModel() {

    var state by mutableStateOf(
        HistoryState(
            weatherEntities = mutableListOf(),
            isLoading = false,
            showDialog = false
        )
    )

    fun loadSaves() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val resp = weatherDao.getWeathers()
            state = state.copy(isLoading = false, weatherEntities = resp)
        }
    }

    fun toggleDialog(show: Boolean) {
        state = state.copy(showDialog = show)
    }

    fun deleteAll() {
        state = state.copy(isLoading = true, showDialog = false)
        viewModelScope.launch {
            weatherDao.clear()
            loadSaves()
        }
        state = state.copy(isLoading = false)
    }
}