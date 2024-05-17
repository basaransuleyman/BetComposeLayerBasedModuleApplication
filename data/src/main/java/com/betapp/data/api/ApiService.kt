package com.betapp.data.api

import com.betapp.data.model.MatchMainResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("statistics/sport/SOCCER/matches")
    suspend fun getMatches(): Response<MatchMainResponse>
}