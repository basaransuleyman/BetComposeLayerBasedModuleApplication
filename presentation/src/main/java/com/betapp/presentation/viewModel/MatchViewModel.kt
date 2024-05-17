package com.betapp.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.betapp.core.BaseViewModel
import com.betapp.core.navigation.NavigationService
import com.betapp.domain.model.Match
import com.betapp.domain.usecase.GetFavoriteMatchesUseCase
import com.betapp.domain.usecase.GetFinishedMatchesUseCase
import com.betapp.domain.usecase.ToggleFavoriteUseCase
import com.betapp.presentation.uievent.MatchUIEvent
import com.betapp.presentation.uistate.MatchUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val getFinishedMatchesUseCase: GetFinishedMatchesUseCase,
    private val navigator: NavigationService,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteMatchesUseCase: GetFavoriteMatchesUseCase
) : BaseViewModel<MatchUIState, MatchUIEvent>(MatchUIState()) {

    init {
        viewModelScope.launch {
            onEvent(MatchUIEvent.LoadInitialMatches)
        }
    }

    override suspend fun handleEvent(event: MatchUIEvent) {
        when (event) {
            MatchUIEvent.LoadInitialMatches -> loadMatches()
            is MatchUIEvent.OnMatchClicked -> navigateToMatchDetail(event.matchId)
            is MatchUIEvent.OnToggleFavoriteClicked -> toggleFavorite(event.match)
            is MatchUIEvent.LoadMatchDetail -> loadMatchDetail(event.matchId)
        }
    }

    private suspend fun loadMatches() {
        viewModelScope.launch {
            getFinishedMatchesUseCase.getMatches()
                .onStart {
                    updateUiState { copy(isLoading = true) }
                }
                .catch { error ->
                    updateUiState { copy(error = error, isLoading = false) }
                }
                .collect { matches ->
                    val favoriteMatches = getFavoriteMatchesUseCase()
                    val matchesWithFavorites = matches.map { match ->
                        match.copy(isFavorite = favoriteMatches.any { it.id == match.id })
                    }
                    updateUiState {
                        copy(
                            matches = matchesWithFavorites,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    private fun navigateToMatchDetail(matchId: Long) {
        navigator.navigateTo("detail/$matchId")
    }

    private suspend fun loadMatchDetail(matchId: Long) {
        try {
            val match = getFinishedMatchesUseCase.getMatchById(matchId)
            match?.let {
                updateUiState { copy(selectedMatch = it) }
            }
        } catch (e: Exception) {
            updateUiState { copy(error = e) }
        }
    }

    private fun toggleFavorite(match: Match) {
        viewModelScope.launch {
            toggleFavoriteUseCase.toggleFavorite(match)
            val updatedMatches = uiState.value.matches?.map {
                if (it.id == match.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            updateUiState { copy(matches = updatedMatches) }
        }
    }
}