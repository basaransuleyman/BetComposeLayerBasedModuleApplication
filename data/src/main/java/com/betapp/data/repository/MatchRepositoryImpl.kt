package com.betapp.data.repository

import com.betapp.core.network.handleCall
import com.betapp.data.api.ApiService
import com.betapp.data.mapper.MatchMapper
import com.betapp.data.mapper.entityToDomainModel
import com.betapp.data.mapper.toEntity
import com.betapp.data.persistence.MatchDao
import com.betapp.domain.model.Match
import com.betapp.domain.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val apiService: ApiService, // Remote
    private val matchDao: MatchDao // Local
) : MatchRepository {

    override suspend fun getMatches(): List<Match> = withContext(Dispatchers.IO) {
        val matchResponse = handleCall {
            apiService.getMatches()
        }
        val favoriteMatches = matchDao.getFavoriteMatches().map { it.entityToDomainModel() }
        val matches = MatchMapper.mapToMatchResponse(matchResponse.data, favoriteMatches)

        matches.forEach { match ->
            matchDao.insertMatch(match.toEntity())
        }

        matchDao.getAllMatches().map { it.entityToDomainModel() }
    }

    override suspend fun getMatchById(matchId: Long): Match? = withContext(Dispatchers.IO) {
        matchDao.getAllMatches().find { it.id == matchId }?.entityToDomainModel()
    }

    override suspend fun insertMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.insertMatch(match.toEntity())
    }

    override suspend fun updateMatch(match: Match) = withContext(Dispatchers.IO) {
        matchDao.updateMatch(match.toEntity())
    }

    override suspend fun getFavoriteMatches(): List<Match> = withContext(Dispatchers.IO) {
        matchDao.getFavoriteMatches().map { it.entityToDomainModel() }
    }
}