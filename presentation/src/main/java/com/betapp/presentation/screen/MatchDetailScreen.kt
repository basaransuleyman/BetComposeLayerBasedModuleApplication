package com.betapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.betapp.presentation.uievent.MatchUIEvent
import com.betapp.presentation.viewModel.MatchViewModel

@Composable
fun MatchDetailScreen(matchId: Long) {

    val viewModel: MatchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(matchId) {
        viewModel.onEvent(MatchUIEvent.LoadMatchDetail(matchId))
    }

    uiState.selectedMatch?.let { match ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Match ID: ${match.id}")
            Text(text = "Start Time: ${match.startTime}")
            Text(text = "Home Team: ${match.homeTeam.name}")
            Text(text = "Away Team: ${match.awayTeam.name}")
            Text(text = "Score: ${match.score.homeTeamScore} - ${match.score.awayTeamScore}")
        }
    } ?: run {
        Text(text = "Loading...")
    }
}
