package com.qndev.moviesapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.qndev.moviesapp.data.local.mapper.toMovieModel
import com.qndev.moviesapp.data.local.movie.MovieEntity
import com.qndev.moviesapp.presentation.screens.movie_list.MovieListScreen
import com.qndev.moviesapp.presentation.screens.movie_list.MovieListState
import com.qndev.moviesapp.util.MOVIE_LIST_TEST_TAG
import com.qndev.moviesapp.util.SEARCH_BAR_TEST_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private val mockMovie = MovieEntity(
        backdropPath = "/2KGxQFV9Wp1MshPBf8BuqWUgVAz.jpg",
        id = 940551,
        originalTitle = "Migration",
        overview = "After a migrating duck family alights on their pond with thrilling tales of far-flung places, the Mallard family embarks on a family road trip, from New England, to New York City, to tropical Jamaica.",
        posterPath = "/ldfCF9RhR40mppkzmftxapaHeTo.jpg",
        mediaType = "movie",
        adult = false,
        title = "Migration",
        originalLanguage = "en",
        genreIds = listOf(16, 28, 12, 35, 10751),
        popularity = 654.061,
        releaseDate = "2023-12-06",
        video = false,
        voteAverage = 7.482,
        voteCount = 1298,

        page = 1,
        timeStamp = 1
    ).toMovieModel()

    @Before
    fun setupScreen() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MovieListScreen(
                navController = navController,
                movieListState = MovieListState(
                    isLoading = false,
                    movieListPage = 1,
                    movieList = listOf(mockMovie),
                ),
                onLoadMore = {},
                onSearchTextChange = {}
            )
        }
    }

    @Test
    fun movieListScreen_verifyContentDisplayed() {
        // Verify that the list of movies is displayed
        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun movieListScreen_verifySearchBarDisplayed() {
        val searchQuery = "test"
        // Verify that the search bar is displayed and can be entered
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).performTextInput(searchQuery)
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertTextEquals(searchQuery)
    }
}