package com.example.weatherwise.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.weatherwise.R

class WeatherImageProvider {
    companion object {
        @Composable
        fun getWeatherImage(icon: String): Painter {
            when (icon) {
                "01d" -> return painterResource(id = R.drawable.i01d)
                "01n" -> return painterResource(id = R.drawable.i01n)
                "02d" -> return painterResource(id = R.drawable.i02d)
                "02n" -> return painterResource(id = R.drawable.i02n)
                "03d" -> return painterResource(id = R.drawable.i03d)
                "03n" -> return painterResource(id = R.drawable.i03n)
                "04d" -> return painterResource(id = R.drawable.i04d)
                "04n" -> return painterResource(id = R.drawable.i04n)
                "50d" -> return painterResource(id = R.drawable.i50d)
                "50n" -> return painterResource(id = R.drawable.i50n)
                "10d" -> return painterResource(id = R.drawable.i10d)
                "10n" -> return painterResource(id = R.drawable.i10n)
                "11n" -> return painterResource(id = R.drawable.i11n)
                "11d" -> return painterResource(id = R.drawable.i11d)
                "13n" -> return painterResource(id = R.drawable.i13n)
                "13d" -> return painterResource(id = R.drawable.i13d)

            }
            return painterResource(id = R.drawable.unknown_icon)
        }
    }
}