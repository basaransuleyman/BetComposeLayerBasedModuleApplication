package com.betapp.data.model

data class MatchResponse(
    val i: Long,
    val sgi: Long,
    val d: Long,
    val st: String,
    val bri: Long,
    val ht: TeamResponse,
    val at: TeamResponse,
    val sc: ScoreResponse,
    val to: TournamentResponse,
    val v: String
)
