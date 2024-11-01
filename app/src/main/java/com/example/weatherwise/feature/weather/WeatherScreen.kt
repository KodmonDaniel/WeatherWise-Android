package com.example.weatherwise.feature.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.data.datasource.CityWeather
import com.example.weatherwise.ui.custom.CustomIcon
import com.example.weatherwise.ui.custom.CustomLoading
import com.example.weatherwise.ui.custom.CustomSaving
import com.example.weatherwise.ui.custom.EmptyScreen
import com.example.weatherwise.ui.theme.Purple80
import com.example.weatherwise.ui.theme.grey
import com.example.weatherwise.ui.theme.robotoFont
import com.example.weatherwise.ui.theme.textSecondary
import com.example.weatherwise.utils.WeatherCityId
import com.example.weatherwise.utils.WeatherConverter
import com.example.weatherwise.utils.WeatherImageProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    openDetails: (WeatherEntity, Boolean) -> Unit,
    openAbout: () -> Unit
) {
    val viewModel: WeatherViewModel = hiltViewModel()

    val context = LocalContext.current
    val analytics = remember { FirebaseAnalytics.getInstance(context) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.app_name),
                            fontFamily = robotoFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { throw RuntimeException("Test Crash") },
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                        IconButton(
                            onClick = { viewModel.loadWeathersFromApi() },
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                        }
                        IconButton(
                            onClick = { openAbout() },
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (viewModel.state.showDialog) ConfirmDialog(viewModel)
        if (viewModel.state.cityWeathers.isEmpty()) EmptyScreen() else
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(viewModel.state.cityWeathers) { element ->
                    WeatherRowElement(
                        element = element,
                        viewModel = viewModel,
                        openDetails = openDetails,
                        analytics = analytics
                    )
                }
            }
        if (viewModel.state.isLoading) CustomLoading()
        if (viewModel.state.isSaving) CustomSaving()

    }
}

@Composable
fun WeatherRowElement(
    element: CityWeather,
    viewModel: WeatherViewModel,
    openDetails: (WeatherEntity, Boolean) -> Unit,
    analytics: FirebaseAnalytics
) {
    return Column(
        modifier = Modifier.clickable {

            analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                param(FirebaseAnalytics.Param.ITEM_ID, WeatherCityId.getCityId(element.name))
                param(FirebaseAnalytics.Param.ITEM_NAME, element.name)
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "city_details")
            }

            val weatherEntity = WeatherConverter.convertToRoomWeather(element)
            openDetails(weatherEntity, false)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)

        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                CustomIcon(
                    icon = WeatherImageProvider.getWeatherImage(icon = element.weather[0].icon),
                    background = Purple80
                )
                Column {
                    Text(
                        text = element.name,
                        fontFamily = robotoFont,
                        fontSize = 16.sp
                    )
                    Text(
                        text = element.weather[0].description,
                        fontFamily = robotoFont,
                        fontSize = 14.sp,
                        color = textSecondary
                    )
                }
            }
            IconButton(
                onClick = { viewModel.saveRequest(element) }
            ) {
                Icon(Icons.Filled.AddCircle, contentDescription = null, tint = grey)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(grey)
                .height(1.dp)
        )
    }
}

@Composable
fun ConfirmDialog(viewModel: WeatherViewModel) {
    AlertDialog(
        icon = {
            Icon(painterResource(id = R.drawable.save), contentDescription = "Save Icon")
        },
        title = {
            Text(text = stringResource(R.string.save_city_weather))
        },
        text = {
            Text(text = stringResource(R.string.save_confirm))
        },
        onDismissRequest = {
            viewModel.closeDialog()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.saveWeather()
                }
            ) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.closeDialog()
                }
            ) {
                Text(stringResource(R.string.no))
            }
        }
    )
}