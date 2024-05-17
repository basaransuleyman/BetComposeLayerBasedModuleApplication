package com.betapp.data.mapper

import com.betapp.data.persistence.MatchEntity
import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament

fun MatchEntity.entityToDomainModel(): Match {
    return Match(
        id = this.id,
        startTime = this.startTime,
        homeTeam = Team(id = this.homeTeamId, name = this.homeTeamName),
        awayTeam = Team(id = this.awayTeamId, name = this.awayTeamName),
        score = Score(
            allTeamScore = this.allTeamScore,
            homeTeamScore = this.homeTeamScore,
            awayTeamScore = this.awayTeamScore
        ),
        tournament = Tournament(id = this.tournamentId, name = this.tournamentName, flagUrl = this.tournamentFlagUrl),
        isFavorite = this.isFavorite
    )
}

fun Match.toEntity(): MatchEntity {
    return MatchEntity(
        id = this.id,
        homeTeamId = this.homeTeam.id,
        homeTeamName = this.homeTeam.name,
        awayTeamId = this.awayTeam.id,
        awayTeamName = this.awayTeam.name,
        homeTeamScore = this.score.homeTeamScore,
        awayTeamScore = this.score.awayTeamScore,
        startTime = this.startTime,
        tournamentId = this.tournament.id,
        tournamentName = this.tournament.name,
        tournamentFlagUrl = this.tournament.flagUrl,
        allTeamScore = this.score.allTeamScore,
        isFavorite = this.isFavorite
    )
}