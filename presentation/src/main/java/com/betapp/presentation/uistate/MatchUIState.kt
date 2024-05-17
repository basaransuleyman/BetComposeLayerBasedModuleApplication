package com.betapp.presentation.uistate

import androidx.compose.runtime.Immutable
import com.betapp.domain.model.Match

@Immutable
data class MatchUIState(
    val isLoading: Boolean = false,
    val matches: List<Match>? = null,
    val error: Throwable? = null,
    val selectedMatch: Match? = null
)