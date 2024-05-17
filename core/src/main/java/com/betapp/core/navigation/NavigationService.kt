package com.betapp.core.navigation

import androidx.navigation.NavOptionsBuilder

interface NavigationService {
    fun navigateTo(destination: String, navOptions: NavOptionsBuilder.() -> Unit = {})
}