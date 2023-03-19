package com.pujol.kitchenrecipesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pujol.kitchenrecipesapp.presentation.components.TopAppBarCompose
import com.pujol.kitchenrecipesapp.presentation.navigation.Route
import com.pujol.kitchenrecipesapp.presentation.screens.detail.DetailScreen
import com.pujol.kitchenrecipesapp.presentation.screens.home.HomeScreen
import com.pujol.kitchenrecipesapp.presentation.screens.map.MapScreen
import com.pujol.kitchenrecipesapp.ui.theme.KitchenRecipesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KitchenRecipesAppTheme {

                val snackBarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                val topApBarTitle = remember {
                    mutableStateOf("")
                }

                val topAppBarIcon = remember {
                    mutableStateOf<ImageVector?>(null)
                }

                val topAppBarOnNavigationClick = remember {
                    mutableStateOf<(() -> Unit)?>(null)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {
                            TopAppBarCompose(
                                title = topApBarTitle.value,
                                navigationIcon = topAppBarIcon.value,
                                onNavigationClick = topAppBarOnNavigationClick.value
                            )
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.HOME_SCREEN,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(Route.HOME_SCREEN) {

                                topApBarTitle.value =
                                    stringResource(id = R.string.title_topbar_home)
                                topAppBarIcon.value = Icons.Filled.Home

                                HomeScreen(snackBarHostState) { idMeal ->
                                    navController.navigate(
                                        Route.DETAIL_SCREEN +
                                                "/$idMeal"
                                    )
                                }
                            }
                            composable(
                                Route.DETAIL_SCREEN + "/{idMeal}",
                                arguments = listOf(
                                    navArgument("idMeal") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                topApBarTitle.value =
                                    stringResource(id = R.string.title_topbar_post)
                                topAppBarIcon.value = Icons.Filled.ArrowBack
                                topAppBarOnNavigationClick.value = {
                                    navController.popBackStack()
                                }
                                val idMeal = it.arguments?.getString("idMeal")!!
                                DetailScreen(
                                    idMeal = idMeal
                                ) { latitude, longitude ->
                                    navController.navigate(Route.MAP_SCREEN + "/$latitude/$longitude")
                                }
                            }

                            composable(
                                Route.MAP_SCREEN + "/{latitude}/{longitude}",
                                arguments = listOf(
                                    navArgument("latitude") {
                                        type = NavType.StringType
                                    },
                                    navArgument("longitude") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                topApBarTitle.value =
                                    stringResource(id = R.string.title_topbar_map)
                                topAppBarIcon.value = Icons.Filled.ArrowBack
                                topAppBarOnNavigationClick.value = {
                                    navController.popBackStack()
                                }
                                val latitude = it.arguments?.getString("latitude")!!
                                val longitude = it.arguments?.getString("longitude")!!
                                MapScreen(
                                    latitude = latitude, longitude = longitude
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}