package com.betapp.domain.usecase

import com.betapp.domain.model.Match
import com.betapp.domain.repository.MatchRepository
import javax.inject.Inject

class GetFavoriteMatchesUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {
    suspend operator fun invoke(): List<Match> {
        return matchRepository.getFavoriteMatches()
    }
}