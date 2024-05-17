package com.betapp.data.mapper

import com.betapp.data.model.MatchResponse
import com.betapp.data.model.ScoreResponse
import com.betapp.data.model.TeamResponse
import com.betapp.data.model.TournamentResponse
import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament

object MatchMapper {

    private fun mapTeam(dataTeam: TeamResponse?): Team? {
        return dataTeam?.let {
            Team(
                id = it.i,
                name = it.n
            )
        }
    }

    private fun mapScore(dataScore: ScoreResponse?): Score? {
        return dataScore?.let {
            Score(
                allTeamScore = it.st,
                homeTeamScore = it.ht?.r ?: 0,
                awayTeamScore = it.at?.r ?: 0
            )
        }
    }

    private fun mapTournament(dataTournament: TournamentResponse?): Tournament? {
        return dataTournament?.let {
            Tournament(
                id = it.i,
                name = it.n,
                flagUrl = it.flag
            )
        }
    }

    fun mapToDomain(dataMatch: MatchResponse?, favoriteMatches: List<Match>): Match? {
        return dataMatch?.let {
            Match(
                id = it.i,
                startTime = it.d,
                homeTeam = mapTeam(it.ht) ?: return null,
                awayTeam = mapTeam(it.at) ?: return null,
                score = mapScore(it.sc) ?: return null,
                tournament = mapTournament(it.to) ?: return null,
                isFavorite = favoriteMatches.any { favoriteMatch -> favoriteMatch.id == it.i }
            )
        }
    }

    fun mapToMatchResponse(dataMatches: List<MatchResponse?>?, favoriteMatches: List<Match>): List<Match> {
        return dataMatches?.mapNotNull { mapToDomain(it, favoriteMatches) } ?: emptyList()
    }
}