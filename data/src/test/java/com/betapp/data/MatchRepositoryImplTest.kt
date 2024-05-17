package com.betapp.data

import com.betapp.data.api.ApiService
import com.betapp.data.mapper.toEntity
import com.betapp.data.model.MatchMainResponse
import com.betapp.data.model.MatchResponse
import com.betapp.data.model.ScoreResponse
import com.betapp.data.model.TeamResponse
import com.betapp.data.model.TeamScoreResponse
import com.betapp.data.model.TournamentResponse
import com.betapp.data.persistence.MatchDao
import com.betapp.data.persistence.MatchEntity
import com.betapp.data.repository.MatchRepositoryImpl
import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import com.betapp.domain.repository.MatchRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

import io.mockk.*

@ExperimentalCoroutinesApi
class MatchRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var matchDao: MatchDao
    private lateinit var matchRepository: MatchRepository

    @Before
    fun setUp() {
        apiService = mockk()
        matchDao = mockk()
        matchRepository = MatchRepositoryImpl(apiService, matchDao)
    }

    @Test
    fun `getMatches should return list of matches`() = runBlocking {
        val matchResponse = MatchResponse(
            i = 1L,
            sgi = 2L,
            d = 1625190000L,
            st = "finished",
            bri = 3L,
            ht = TeamResponse(i = 10L, n = "Home Team", p = 1, sn = "HT"),
            at = TeamResponse(i = 20L, n = "Away Team", p = 1, sn = "AT"),
            sc = ScoreResponse(
                st = 3,
                ht = TeamScoreResponse(r = 1, c = 1),
                at = TeamScoreResponse(r = 2, c = 1),
                abbr = "S"
            ),
            to = TournamentResponse(i = 100L, n = "Tournament", sn = "T", p = 1, flag = "flag_url"),
            v = "venue"
        )
        val matchMainResponse = MatchMainResponse(
            success = true,
            data = listOf(matchResponse)
        )

        coEvery { apiService.getMatches() } returns Response.success(matchMainResponse)
        coEvery { matchDao.getFavoriteMatches() } returns emptyList()
        coEvery { matchDao.insertMatch(any()) } just Runs
        coEvery { matchDao.getAllMatches() } returns listOf(
            MatchEntity(
                id = 1L,
                homeTeamId = 10L,
                homeTeamName = "Home Team",
                awayTeamId = 20L,
                awayTeamName = "Away Team",
                homeTeamScore = 1,
                awayTeamScore = 2,
                startTime = 1625190000L,
                tournamentId = 100L,
                tournamentName = "Tournament",
                tournamentFlagUrl = "flag_url",
                allTeamScore = 3,
                isFavorite = false
            )
        )

        val matches = matchRepository.getMatches()

        assertNotNull(matches)
        assertEquals(1, matches.size)
        assertEquals(1L, matches[0].id)
        assertEquals("Home Team", matches[0].homeTeam.name)
        assertEquals("Away Team", matches[0].awayTeam.name)
        assertEquals(3, matches[0].score.allTeamScore)
    }

    @Test
    fun `getMatchById should return correct match`() = runBlocking {
        val matchEntity = MatchEntity(
            id = 1L,
            homeTeamId = 10L,
            homeTeamName = "Home Team",
            awayTeamId = 20L,
            awayTeamName = "Away Team",
            homeTeamScore = 1,
            awayTeamScore = 2,
            startTime = 1625190000L,
            tournamentId = 100L,
            tournamentName = "Tournament",
            tournamentFlagUrl = "flag_url",
            allTeamScore = 3,
            isFavorite = false
        )

        coEvery { matchDao.getAllMatches() } returns listOf(matchEntity)

        val match = matchRepository.getMatchById(1L)

        assertNotNull(match)
        assertEquals(1L, match?.id)
        assertEquals("Home Team", match?.homeTeam?.name)
        assertEquals("Away Team", match?.awayTeam?.name)
        assertEquals(3, match?.score?.allTeamScore)
    }

    @Test
    fun `insertMatch should call insertMatch on matchDao`() = runBlocking {
        val match = Match(
            id = 1L,
            startTime = 1625190000L,
            homeTeam = Team(id = 10L, name = "Home Team"),
            awayTeam = Team(id = 20L, name = "Away Team"),
            score = Score(allTeamScore = 3, homeTeamScore = 1, awayTeamScore = 2),
            tournament = Tournament(id = 100L, name = "Tournament", flagUrl = "flag_url"),
            isFavorite = false
        )

        coEvery { matchDao.insertMatch(any()) } just Runs

        matchRepository.insertMatch(match)

        coVerify { matchDao.insertMatch(match.toEntity()) }
    }

    @Test
    fun `updateMatch should call updateMatch on matchDao`() = runBlocking {
        val match = Match(
            id = 1L,
            startTime = 1625190000L,
            homeTeam = Team(id = 10L, name = "Home Team"),
            awayTeam = Team(id = 20L, name = "Away Team"),
            score = Score(allTeamScore = 3, homeTeamScore = 1, awayTeamScore = 2),
            tournament = Tournament(id = 100L, name = "Tournament", flagUrl = "flag_url"),
            isFavorite = true
        )

        coEvery { matchDao.updateMatch(any()) } just Runs

        matchRepository.updateMatch(match)

        coVerify { matchDao.updateMatch(match.toEntity()) }
    }

    @Test
    fun `getFavoriteMatches should return list of favorite matches`() = runBlocking {
        val matchEntity = MatchEntity(
            id = 1L,
            homeTeamId = 10L,
            homeTeamName = "Home Team",
            awayTeamId = 20L,
            awayTeamName = "Away Team",
            homeTeamScore = 1,
            awayTeamScore = 2,
            startTime = 1625190000L,
            tournamentId = 100L,
            tournamentName = "Tournament",
            tournamentFlagUrl = "flag_url",
            allTeamScore = 3,
            isFavorite = true
        )

        coEvery { matchDao.getFavoriteMatches() } returns listOf(matchEntity)

        val favoriteMatches = matchRepository.getFavoriteMatches()

        assertNotNull(favoriteMatches)
        assertEquals(1, favoriteMatches.size)
        assertEquals(1L, favoriteMatches[0].id)
        assertEquals(true, favoriteMatches[0].isFavorite)
    }
}