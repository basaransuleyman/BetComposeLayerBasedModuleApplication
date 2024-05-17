package com.betapp.domain

import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import com.betapp.domain.repository.MatchRepository
import com.betapp.domain.usecase.GetFavoriteMatchesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavoriteMatchesUseCase {
    private lateinit var matchRepository: MatchRepository
    private lateinit var getFavoriteMatchesUseCase: GetFavoriteMatchesUseCase

    @Before
    fun setUp() {
        matchRepository = mockk()
        getFavoriteMatchesUseCase = GetFavoriteMatchesUseCase(matchRepository)
    }

    @Test
    fun `invoke should return favorite matches`() = runBlocking {
        val favoriteMatches = listOf(
            Match(
                1L,
                125125L,
                Team(1L, "Team A"),
                Team(2L, "Team B"),
                Score(5, 3, 2),
                Tournament(1L, "Tournament A", "flag_url"),
                isFavorite = true
            )
        )
        coEvery { matchRepository.getFavoriteMatches() } returns favoriteMatches

        val result = getFavoriteMatchesUseCase()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(1L, result[0].id)
        assertEquals(true, result[0].isFavorite)
    }
}