package com.betapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(
    navigator: Navigator,
    homeScreen: @Composable () -> Unit,
    detailScreen: @Composable (Long) -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navigator.actions.collectLatest { action ->
            when (action) {
                Navigator.Action.Back -> navController.popBackStack()
                is Navigator.Action.Navigate -> navController.navigate(
                    route = action.destination,
                    builder = action.navOptions
                )
            }
        }
    }

    NavHost(navController, startDestination = Destination.home.route) {
        composable(Destination.home.route) {
            homeScreen()
        }
        composable(
            route = Destination.detail.route,
            arguments = Destination.detail.arguments
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getLong("matchId") ?: return@composable
            detailScreen(matchId)
        }
    }

}
