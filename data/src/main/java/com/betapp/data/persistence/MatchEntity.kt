package com.betapp.data.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey val id: Long,
    val homeTeamId: Long,
    val homeTeamName: String,
    val awayTeamId: Long,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val startTime: Long,
    val tournamentId: Long,
    val tournamentName: String,
    val tournamentFlagUrl: String,
    val allTeamScore: Int,
    val isFavorite: Boolean
)
