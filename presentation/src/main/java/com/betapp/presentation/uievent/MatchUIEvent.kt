package com.betapp.presentation.uievent

import com.betapp.domain.model.Match

sealed interface MatchUIEvent {
    data object LoadInitialMatches : MatchUIEvent
    data class OnMatchClicked(val matchId: Long) : MatchUIEvent
    data class LoadMatchDetail(val matchId: Long) : MatchUIEvent
    data class OnToggleFavoriteClicked(val match: Match) : MatchUIEvent
}