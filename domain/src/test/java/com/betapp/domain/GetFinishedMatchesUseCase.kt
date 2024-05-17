package com.betapp.domain

import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import com.betapp.domain.repository.MatchRepository
import com.betapp.domain.usecase.GetFinishedMatchesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFinishedMatchesUseCase {

    private lateinit var matchRepository: MatchRepository
    private lateinit var getFinishedMatchesUseCase: GetFinishedMatchesUseCase

    @Before
    fun setUp() {
        matchRepository = mockk()
        getFinishedMatchesUseCase = GetFinishedMatchesUseCase(matchRepository)
    }

    @Test
    fun `getMatches should emit filtered and sorted matches`() = runBlocking {
        val matches = listOf(
            Match(
                1L,
                125125L,
                Team(1L, "Team A"),
                Team(2L, "Team B"),
                Score(5, 3, 2),
                Tournament(1L, "Tournament A", "flag_url"),
                isFavorite = false
            ),
            Match(
                2L,
                1251256L,
                Team(1L, "Team A"),
                Team(2L, "Team B"),
                Score(4, 3, 1),
                Tournament(1L, "Tournament A", "flag_url"),
                isFavorite = false
            ),
            Match(
                3L,
                125128L,
                Team(1L, "Team A"),
                Team(2L, "Team B"),
                Score(5, 2, 3),
                Tournament(2L, "Tournament B", "flag_url"),
                isFavorite = false
            )
        )
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
        coEvery { matchRepository.getMatches() } returns matches
        coEvery { matchRepository.getFavoriteMatches() } returns favoriteMatches

        val result = getFinishedMatchesUseCase.getMatches().toList()

        assertEquals(1, result.size)
        assertEquals(2, result[0].size)
        assertEquals(1L, result[0][0].id)
        assertEquals(3L, result[0][1].id)
        assertEquals(true, result[0][0].isFavorite)
        assertEquals(false, result[0][1].isFavorite)
    }

    @Test
    fun `getMatchById should return correct match`() = runBlocking {
        val match = Match(
            1L,
            125125L,
            Team(1L, "Team A"),
            Team(2L, "Team B"),
            Score(5, 3, 2),
            Tournament(1L, "Tournament A", "flag_url"),
            isFavorite = false
        )
        coEvery { matchRepository.getMatchById(1L) } returns match

        val result = getFinishedMatchesUseCase.getMatchById(1L)

        assertNotNull(result)
        assertEquals(1L, result?.id)
    }
}

