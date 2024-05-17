package com.betapp.domain.usecase

import com.betapp.domain.model.Match
import com.betapp.domain.repository.MatchRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {
    suspend fun toggleFavorite(match: Match) {
        val updatedMatch = match.copy(isFavorite = !match.isFavorite)
        matchRepository.updateMatch(updatedMatch)
    }
}
