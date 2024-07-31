package com.yogigupta1206.m2ppokemon.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yogigupta1206.m2ppokemon.presentation.home.HomeScreen
import com.yogigupta1206.m2ppokemon.presentation.pokemon_info.PokemonDetailsScreen

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route,
        modifier = Modifier
    ) {

        composable(Screens.HomePage.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screens.PokemonDetailsPage.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            PokemonDetailsScreen(navController = navController)
        }


    }
}

