package com.betapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Match(
    val id: Long,
    val startTime: Long,
    val homeTeam: Team,
    val awayTeam: Team,
    val score: Score,
    val tournament: Tournament,
    val isFavorite: Boolean
)