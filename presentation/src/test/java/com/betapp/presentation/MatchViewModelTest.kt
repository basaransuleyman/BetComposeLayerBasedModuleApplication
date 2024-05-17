package com.betapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.betapp.core.navigation.NavigationService
import com.betapp.domain.model.Match
import com.betapp.domain.model.Score
import com.betapp.domain.model.Team
import com.betapp.domain.model.Tournament
import com.betapp.domain.usecase.GetFavoriteMatchesUseCase
import com.betapp.domain.usecase.GetFinishedMatchesUseCase
import com.betapp.domain.usecase.ToggleFavoriteUseCase
import com.betapp.presentation.uievent.MatchUIEvent
import com.betapp.presentation.viewModel.MatchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MatchViewModelTest {

@get:Rule
val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

private val dispatcher = StandardTestDispatcher()

private lateinit var getFinishedMatchesUseCase: GetFinishedMatchesUseCase
private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
private lateinit var getFavoriteMatchesUseCase: GetFavoriteMatchesUseCase
private lateinit var navigator: NavigationService
private lateinit var matchViewModel: MatchViewModel

@Before
fun setUp() {
    Dispatchers.setMain(dispatcher)

    getFinishedMatchesUseCase = mockk()
    toggleFavoriteUseCase = mockk()
    getFavoriteMatchesUseCase = mockk()
    navigator = mockk(relaxed = true)
    matchViewModel = MatchViewModel(
        getFinishedMatchesUseCase,
        navigator,
        toggleFavoriteUseCase,
        getFavoriteMatchesUseCase
    )

    coEvery { getFinishedMatchesUseCase.getMatches() } returns flowOf(emptyList())
    coEvery { getFavoriteMatchesUseCase() } returns emptyList()
}

@After
fun tearDown() {
    Dispatchers.resetMain()
}


    @Test
    fun `loadInitialMatches updates UI state with matches`() = runTest {
        // Mocking
        val matches = listOf(
            Match(
                id = 1L,
                startTime = 1625190000L,
                homeTeam = Team(id = 10L, name = "Home Team"),
                awayTeam = Team(id = 20L, name = "Away Team"),
                score = Score(allTeamScore = 5, homeTeamScore = 3, awayTeamScore = 2),
                tournament = Tournament(id = 100L, name = "Tournament A", flagUrl = "https://example.com/flagA.png"),
                isFavorite = false
            ),
            Match(
                id = 2L,
                startTime = 1625276400L,
                homeTeam = Team(id = 11L, name = "Home Team B"),
                awayTeam = Team(id = 21L, name = "Away Team B"),
                score = Score(allTeamScore = 5, homeTeamScore = 4, awayTeamScore = 1),
                tournament = Tournament(id = 101L, name = "Tournament B", flagUrl = "https://example.com/flagB.png"),
                isFavorite = false
            )
        )
        coEvery { getFinishedMatchesUseCase.getMatches() } returns flowOf(matches)

        // Trigger the event
        matchViewModel.onEvent(MatchUIEvent.LoadInitialMatches)

        // Move dispatcher forward
        advanceUntilIdle()

        // Assert the UI state
        assertEquals(matches, matchViewModel.uiState.value.matches)
        assertFalse(matchViewModel.uiState.value.isLoading)
        assertNull(matchViewModel.uiState.value.error)
    }

    @Test
    fun `loadInitialMatches handles error`() = runTest {
        // Mocking
        val exception = Exception("Network error")
        coEvery { getFinishedMatchesUseCase.getMatches() } returns flow { throw exception }

        matchViewModel.onEvent(MatchUIEvent.LoadInitialMatches)

        advanceUntilIdle()

        // Assert the UI state
        assertEquals(exception, matchViewModel.uiState.value.error)
        assertFalse(matchViewModel.uiState.value.isLoading)
    }

    @Test
    fun `navigateToMatchDetail triggers navigation`() = runTest {
        val matchId = 1L

        matchViewModel.onEvent(MatchUIEvent.OnMatchClicked(matchId))

        advanceUntilIdle()

        coVerify { navigator.navigateTo(eq("detail/$matchId")) }
    }

    @Test
    fun `loadMatchDetail updates selected match`() = runTest {
        val match = Match(
            id = 1L,
            startTime = 1625190000L,
            homeTeam = Team(id = 10L, name = "Home Team"),
            awayTeam = Team(id = 20L, name = "Away Team"),
            score = Score(allTeamScore = 5, homeTeamScore = 3, awayTeamScore = 2),
            tournament = Tournament(id = 100L, name = "Tournament A", flagUrl = "https://example.com/flagA.png"),
            isFavorite = false
        )
        coEvery { getFinishedMatchesUseCase.getMatchById(1L) } returns match

        matchViewModel.onEvent(MatchUIEvent.LoadMatchDetail(1L))

        // Move dispatcher forward
        advanceUntilIdle()

        assertEquals(match, matchViewModel.uiState.value.selectedMatch)
    }

    @Test
    fun `loadMatchDetail handles error`() = runTest {
        val exception = Exception("Network error")
        coEvery { getFinishedMatchesUseCase.getMatchById(1L) } throws exception

        matchViewModel.onEvent(MatchUIEvent.LoadMatchDetail(1L))

        // Move dispatcher forward
        advanceUntilIdle()

        // Assert the UI state
        assertEquals(exception.message, "Network error")
        assertNull(matchViewModel.uiState.value.selectedMatch)
    }

    @Test
    fun `toggleFavorite updates match favorite status`() = runTest {
        val match = Match(
            id = 1L,
            startTime = 1625190000L,
            homeTeam = Team(id = 10L, name = "Home Team"),
            awayTeam = Team(id = 20L, name = "Away Team"),
            score = Score(allTeamScore = 5, homeTeamScore = 3, awayTeamScore = 2),
            tournament = Tournament(id = 100L, name = "Tournament A", flagUrl = "https://example.com/flagA.png"),
            isFavorite = false
        )
        val updatedMatch = match.copy(isFavorite = true)
        coEvery { toggleFavoriteUseCase.toggleFavorite(match) } just runs
        coEvery { getFinishedMatchesUseCase.getMatches() } returns flowOf(listOf(match))

        matchViewModel.onEvent(MatchUIEvent.OnToggleFavoriteClicked(match))

        // Move dispatcher forward
        advanceUntilIdle()

        coVerify { toggleFavoriteUseCase.toggleFavorite(match) }
        assertTrue(matchViewModel.uiState.value.matches?.first()?.isFavorite ?: false)
    }
}