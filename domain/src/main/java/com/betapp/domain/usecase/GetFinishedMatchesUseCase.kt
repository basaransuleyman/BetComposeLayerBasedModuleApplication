package com.betapp.domain.usecase

import com.betapp.domain.model.Match
import com.betapp.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFinishedMatchesUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {

    fun getMatches(): Flow<List<Match>> = flow {
        try {
            val matches = matchRepository.getMatches()
            val favoriteMatches = matchRepository.getFavoriteMatches()
            val finishedMatches = filterAndSortMatches(matches, favoriteMatches)
            emit(finishedMatches)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    private fun filterAndSortMatches(matches: List<Match>, favoriteMatches: List<Match>): List<Match> {
        return matches
            .asSequence()
            .filter { it.score.allTeamScore == 5 } // Filter finished matches
            .sortedBy { it.startTime } // Sort by date
            .map { match ->
                match.copy(isFavorite = favoriteMatches.any { it.id == match.id })
            }
            .toList()
    }


    suspend fun getMatchById(matchId: Long): Match? = matchRepository.getMatchById(matchId)
}