package com.example.weatherwise.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.ui.custom.CustomLoading
import com.example.weatherwise.ui.custom.EmptyScreen
import com.example.weatherwise.ui.theme.darkPink
import com.example.weatherwise.ui.theme.darkPurple
import com.example.weatherwise.ui.theme.grey
import com.example.weatherwise.ui.theme.robotoFont
import com.example.weatherwise.ui.theme.textSecondary
import com.example.weatherwise.utils.DateConverter
import com.example.weatherwise.utils.TempConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    openDetails: (WeatherEntity, Boolean) -> Unit,
    openAbout: () -> Unit
) {
    val viewModel: HistoryViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadSaves()
    }

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
                            onClick = { viewModel.toggleDialog(true) },
                        ) {
                            Icon(
                                painterResource(id = R.drawable.delete_all),
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = { viewModel.loadSaves() },
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
        if (viewModel.state.weatherEntities.isEmpty()) EmptyScreen() else
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(viewModel.state.weatherEntities) { element ->
                    WeatherRowElement(element = element, openDetails = openDetails)

                }
            }
        if (viewModel.state.isLoading) CustomLoading()
    }
}


@Composable
fun WeatherRowElement(element: WeatherEntity, openDetails: (WeatherEntity, Boolean) -> Unit) {
    return Column(
        modifier = Modifier.clickable {
            openDetails(element, true)

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
                TempIcon(element.temp ?: 0.0)
                Column {
                    Text(
                        text = element.city ?: "",
                        fontFamily = robotoFont,
                        fontSize = 16.sp
                    )
                    Text(
                        text = element.desc ?: "",
                        fontFamily = robotoFont,
                        fontSize = 14.sp,
                        color = textSecondary
                    )
                }
            }
            Text(
                text = DateConverter.toDateString(element.date) ?: "",
                fontFamily = robotoFont,
                fontSize = 16.sp
            )
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
fun TempIcon(temp: Double) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(end = 20.dp)

    ) {
        Text(
            "${TempConverter.celsiusFromKelvin(temp)}°C", // Kelvin conversion + truncate
            fontFamily = robotoFont,
            fontWeight = FontWeight.ExtraBold,
            color = darkPurple,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .size(48.dp)
                .background(darkPink, CircleShape)
                .wrapContentHeight()
        )
    }
}

@Composable
fun ConfirmDialog(viewModel: HistoryViewModel) {
    AlertDialog(
        icon = {
            Icon(painterResource(id = R.drawable.delete_all), contentDescription = "Delete Icon")
        },
        title = {
            Text(text = stringResource(R.string.delete_all))
        },
        text = {
            Text(text = stringResource(R.string.delete_all_confirm))
        },
        onDismissRequest = {
            viewModel.toggleDialog(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.deleteAll()
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
