package com.betapp.domain.repository

import com.betapp.domain.model.Match

interface MatchRepository {
    suspend fun getMatches(): List<Match>
    suspend fun getMatchById(matchId: Long): Match?
    suspend fun insertMatch(match: Match)
    suspend fun updateMatch(match: Match)
    suspend fun getFavoriteMatches(): List<Match>
}