package com.betapp.presentation.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.betapp.domain.model.Match
import com.betapp.presentation.components.ErrorText
import com.betapp.presentation.components.LoadingIndicator
import com.betapp.presentation.components.MatchRow
import com.betapp.presentation.components.TournamentRow
import com.betapp.presentation.uievent.MatchUIEvent
import com.betapp.presentation.viewModel.MatchViewModel

@Composable
fun MatchScreen() {
    val viewModel: MatchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingIndicator()
        uiState.error != null -> ErrorText(uiState.error!!)
        uiState.matches != null -> MatchList(uiState.matches!!, viewModel)
    }
}

@Composable
fun MatchList(matches: List<Match>, viewModel: MatchViewModel) {
    val groupedMatches by remember(matches) { derivedStateOf { matches.groupBy { it.tournament } } }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        groupedMatches.forEach { (tournament, matches) ->
            item {
                TournamentRow(tournament = tournament)
            }
            items(matches, key = { it.id }) { match ->
                MatchRow(
                    match = match,
                    onMatchClicked = { viewModel.onEvent(MatchUIEvent.OnMatchClicked(match.id)) },
                    onFavoriteClicked = { viewModel.onEvent(MatchUIEvent.OnToggleFavoriteClicked(match)) }
                )
                Divider()
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}