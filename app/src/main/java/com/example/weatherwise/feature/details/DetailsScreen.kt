package com.example.weatherwise.feature.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.ui.custom.CustomIcon
import com.example.weatherwise.ui.custom.CustomIconButton
import com.example.weatherwise.ui.custom.CustomLoading
import com.example.weatherwise.ui.theme.Purple
import com.example.weatherwise.ui.theme.lightRed
import com.example.weatherwise.ui.theme.robotoFont
import com.example.weatherwise.ui.theme.textSecondary
import com.example.weatherwise.utils.DateConverter
import com.example.weatherwise.utils.TempConverter
import com.example.weatherwise.utils.WeatherImageProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    isSaved: Boolean?,
    onBackPressed: () -> Unit,
    element: WeatherEntity?
) {
    val viewModel: DetailsViewModel = hiltViewModel()

    return Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            element?.city + " - " + stringResource(R.string.details),
                            fontFamily = robotoFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                WeatherImageProvider.getWeatherImage(icon = element?.icon ?: ""),
                                contentDescription = "icon",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp),
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (viewModel.state.showDialog) isSaved?.let {
            element?.let { weatherEntity ->
                ConfirmDialog(
                    weatherEntity,
                    it,
                    viewModel,
                    onBackPressed
                )
            }
        }

        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                //horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .padding(innerPadding),
            ) {
                DetailsBox(
                    icon = painterResource(id = R.drawable.temperature),
                    text = stringResource(
                        R.string.temperature
                    ),
                    desc = stringResource(R.string.celsius),
                    value = TempConverter.celsiusFromKelvin(element?.temp).toString(),
                    unit = "°C"
                )
                DetailsBox(
                    icon = painterResource(id = R.drawable.humidity),
                    text = stringResource(
                        R.string.humidity
                    ),
                    desc = stringResource(R.string.percentage),
                    value = element?.humidity.toString(),
                    unit = "%"
                )
                DetailsBox(
                    icon = painterResource(id = R.drawable.wind),
                    text = stringResource(
                        R.string.wind
                    ),
                    desc = stringResource(R.string.speed),
                    value = element?.wind.toString(),
                    unit = "km/h"
                )
                DetailsBox(
                    icon = painterResource(id = R.drawable.baseline_cloud_24),
                    text = stringResource(
                        R.string.cloud
                    ),
                    desc = stringResource(R.string.coverage),
                    value = element?.clouds.toString(),
                    unit = "%"
                )
            }

            if (isSaved == true) CustomIconButton(
                onClicked = { viewModel.toggleDialog(true) },
                label = stringResource(R.string.delete),
                icon = painterResource(id = R.drawable.delete)
            )
            if (isSaved == false) CustomIconButton(
                onClicked = { viewModel.toggleDialog(true) },
                label = stringResource(R.string.save),
                icon = painterResource(id = R.drawable.save)
            )

            Text(text = "${if (isSaved == true) stringResource(id = R.string.saved) else stringResource(id = R.string.unsaved)} - ${DateConverter.toDateTimeString(element?.date)}",
                modifier = Modifier.padding(bottom = 25.dp),
                fontFamily = robotoFont,
                fontSize = 14.sp
            )
        }
        if (viewModel.state.isLoading) CustomLoading()
    }
}

@Composable
fun DetailsBox(icon: Painter, text: String, desc: String, value: String, unit: String) {
    Card(
        border = BorderStroke(width = 1.0.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIcon(icon = icon, background = Purple)
                Column {
                    Text(
                        text = text,
                        fontFamily = robotoFont,
                        fontSize = 16.sp
                    )
                    Text(
                        text = desc,
                        fontFamily = robotoFont,
                        fontSize = 14.sp,
                        color = textSecondary
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(lightRed)
                    .fillMaxHeight()
                    .height(100.dp)
                    .width(100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Text(
                        text = value,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = robotoFont,
                        fontSize = 24.sp,
                    )
                    Text(
                        text = unit,
                        fontFamily = robotoFont,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    weatherEntity: WeatherEntity,
    isSaved: Boolean,
    viewModel: DetailsViewModel,
    onBackPressed: () -> Unit
) {
    AlertDialog(
        icon = {
            if (isSaved) Icon(
                painterResource(id = R.drawable.delete_all),
                contentDescription = "Save Icon"
            )
            else Icon(painterResource(id = R.drawable.save), contentDescription = "Save Icon")
        },
        title = {
            if (isSaved) Text(text = stringResource(R.string.delete_city_weather))
            else Text(text = stringResource(R.string.save_city_weather))
        },
        text = {
            if (isSaved) Text(text = stringResource(R.string.delete_confirm))
            else Text(text = stringResource(R.string.save_confirm))
        },
        onDismissRequest = {
            viewModel.toggleDialog(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.toggleDialog(false)
                    if (isSaved) {
                        viewModel.delete(weatherEntity.uuid)
                        onBackPressed()
                    } else {
                        viewModel.save(weatherEntity)
                        onBackPressed()
                    }
                }
            ) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.toggleDialog(false)
                }
            ) {
                Text(stringResource(R.string.no))
            }
        }
    )
}