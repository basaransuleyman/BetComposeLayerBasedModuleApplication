package com.betapp.data

import com.betapp.data.mapper.MatchMapper
import com.betapp.data.model.MatchResponse
import com.betapp.data.model.ScoreResponse
import com.betapp.data.model.TeamResponse
import com.betapp.data.model.TeamScoreResponse
import com.betapp.data.model.TournamentResponse
import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import org.junit.Test
import org.junit.Assert.*

class MapperTest {

    @Test
    fun `map valid MatchResponse to Match`() {
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

        val favoriteMatches = listOf<Match>()

        val match = MatchMapper.mapToDomain(matchResponse, favoriteMatches)
        assertNotNull(match)
        assertEquals(1L, match?.id)
        assertEquals("Home Team", match?.homeTeam?.name)
        assertEquals("Away Team", match?.awayTeam?.name)
        assertEquals(3, match?.score?.allTeamScore)
        assertFalse(match?.isFavorite ?: true)
    }

    @Test
    fun `map valid MatchResponse to favorite Match`() {
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

        val favoriteMatches = listOf(
            Match(
                id = 1L,
                startTime = 1625190000L,
                homeTeam = Team(id = 10L, name = "Home Team"),
                awayTeam = Team(id = 20L, name = "Away Team"),
                score = Score(allTeamScore = 3, homeTeamScore = 1, awayTeamScore = 2),
                tournament = Tournament(id = 100L, name = "Tournament", flagUrl = "flag_url"),
                isFavorite = true
            )
        )

        val match = MatchMapper.mapToDomain(matchResponse, favoriteMatches)
        assertNotNull(match)
        assertEquals(1L, match?.id)
        assertEquals("Home Team", match?.homeTeam?.name)
        assertEquals("Away Team", match?.awayTeam?.name)
        assertEquals(3, match?.score?.allTeamScore)
        assertTrue(match?.isFavorite ?: false)
    }

    @Test
    fun `map null MatchResponse to null`() {
        val matchResponse: MatchResponse? = null
        val favoriteMatches = listOf<Match>()
        val match = MatchMapper.mapToDomain(matchResponse, favoriteMatches)
        assertNull(match)
    }

    @Test
    fun `map list of MatchResponse to list of Match`() {
        val matchResponses = listOf(
            MatchResponse(
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
                to = TournamentResponse(
                    i = 100L,
                    n = "Tournament",
                    sn = "T",
                    p = 1,
                    flag = "flag_url"
                ),
                v = "venue"
            )
        )

        val favoriteMatches = listOf<Match>()
        val matches = MatchMapper.mapToMatchResponse(matchResponses, favoriteMatches)
        assertEquals(1, matches.size)
        assertEquals(1L, matches[0].id)
    }

    @Test
    fun `map list with null MatchResponse to list of Match without nulls`() {
        val matchResponses = listOf(
            MatchResponse(
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
                to = TournamentResponse(
                    i = 100L,
                    n = "Tournament",
                    sn = "T",
                    p = 1,
                    flag = "flag_url"
                ),
                v = "venue"
            ),
            null
        )

        val favoriteMatches = listOf<Match>()
        val matches = MatchMapper.mapToMatchResponse(matchResponses, favoriteMatches)
        assertEquals(1, matches.size)
        assertEquals(1L, matches[0].id)
    }
}
