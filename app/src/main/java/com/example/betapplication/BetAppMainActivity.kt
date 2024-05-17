package com.example.betapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.betapp.navigation.AppNavigation
import com.betapp.navigation.Navigator
import com.betapp.presentation.screen.MatchDetailScreen
import com.betapp.presentation.screen.MatchScreen
import com.example.betapplication.ui.theme.BetApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetApplicationTheme {
                AppNavigation(
                    navigator = navigator,
                    homeScreen = {
                        MatchScreen()
                    },
                    detailScreen = {
                        MatchDetailScreen(it)
                    }
                )
            }

        }
    }
}
