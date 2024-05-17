package com.betapp.navigation.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.betapp.navigation.utils.ArgsScreen
import com.betapp.navigation.utils.DestinationRoute

object Detail : ArgsScreen<Long> {
    override val route = "detail/{matchId}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument("matchId") { type = NavType.LongType }
    )

    override fun destination(arg: Long): DestinationRoute = "detail/$arg"
    override fun objectParser(entry: NavBackStackEntry): Long {
        return entry.arguments?.getLong("matchId")
            ?: throw IllegalArgumentException("Match ID is missing")
    }
}