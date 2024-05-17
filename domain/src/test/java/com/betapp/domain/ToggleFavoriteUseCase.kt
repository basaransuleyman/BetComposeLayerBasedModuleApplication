package com.betapp.domain

import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import com.betapp.domain.repository.MatchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import com.betapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleFavoriteUseCase {

    private lateinit var matchRepository: MatchRepository
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        matchRepository = mockk()
        toggleFavoriteUseCase = ToggleFavoriteUseCase(matchRepository)
    }

    @Test
    fun `toggleFavorite should update match favorite status`() = runBlocking {
        val match = Match(
            1L,
            125125L,
            Team(1L, "Team A"),
            Team(2L, "Team B"),
            Score(5, 3, 2),
            Tournament(1L, "Tournament A", "flag_url"),
            isFavorite = false
        )
        val updatedMatch = match.copy(isFavorite = true)

        coEvery { matchRepository.updateMatch(updatedMatch) } returns Unit

        toggleFavoriteUseCase.toggleFavorite(match)

        coVerify { matchRepository.updateMatch(updatedMatch) }
    }
}