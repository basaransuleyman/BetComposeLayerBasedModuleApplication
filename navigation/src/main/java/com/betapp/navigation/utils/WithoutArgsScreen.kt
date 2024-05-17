package com.betapp.navigation.utils

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.betapp.navigation.utils.DestinationRoute
import com.betapp.navigation.utils.NavDestination
import com.betapp.navigation.utils.NodeScreen

abstract class WithoutArgsScreen : NodeScreen, NavDestination<Unit> {
    override val arguments: List<NamedNavArgument> get() = emptyList()
    override fun objectParser(entry: NavBackStackEntry) {}
    override fun destination(arg: Unit): DestinationRoute = route
}