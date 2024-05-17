package com.betapp.navigation.utils

import androidx.navigation.NavBackStackEntry

interface NavDestination<Arg> {
    fun destination(arg: Arg): DestinationRoute
    fun objectParser(entry: NavBackStackEntry): Arg
}
typealias DestinationRoute = String
