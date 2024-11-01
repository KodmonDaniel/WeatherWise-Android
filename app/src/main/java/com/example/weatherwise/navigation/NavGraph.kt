package com.example.weatherwise.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherwise.R
import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.feature.about.About
import com.example.weatherwise.feature.details.DetailsScreen
import com.example.weatherwise.feature.history.HistoryScreen
import com.example.weatherwise.feature.weather.WeatherScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            Surface {
                if (currentRoute == Screen.Weather.route || currentRoute == Screen.History.route) NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Weather.route,
                        onClick = {
                            navController.navigate(Screen.Weather.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.weather),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(R.string.weather))
                        }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.History.route,
                        onClick = {
                            navController.navigate(Screen.History.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.history),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(R.string.history))
                        }
                    )
                }
            }
        },
        content = { cPadding ->
            NavHost(
                modifier = Modifier.padding(cPadding),
                navController = navController,
                startDestination = Screen.Weather.route
            ) {

                composable(Screen.Weather.route) {
                    WeatherScreen(
                        openAbout = { navController.navigate(Screen.About.route) },
                        openDetails = { weatherEntity, isSaved ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "weather_element",
                                value = weatherEntity
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "is_saved",
                                value = isSaved
                            )
                            navController.navigate(Screen.Details.route)
                        }
                    )
                }

                composable(Screen.History.route) {
                    HistoryScreen(
                        openAbout = { navController.navigate(Screen.About.route) },
                        openDetails = { weatherEntity, isSaved ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "weather_element",
                                value = weatherEntity
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "is_saved",
                                value = isSaved
                            )
                            navController.navigate(Screen.Details.route)
                        }
                    )
                }

                composable(Screen.Details.route) {
                    val weatherElement =
                        navController.previousBackStackEntry?.savedStateHandle?.get<WeatherEntity>("weather_element")
                    val isSaved =
                        navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("is_saved")
                    DetailsScreen(
                        isSaved = isSaved,
                        element = weatherElement,
                        onBackPressed = { navController.popBackStack() }
                    )
                }

                composable(Screen.About.route) {
                    About(onBackPressed = { navController.popBackStack() })
                }

            }
        }
    )
}

sealed class Screen(val route: String) {
    data object Weather : Screen("weather")
    data object History : Screen("history")
    data object Details : Screen("details")
    data object About : Screen("about")

}
