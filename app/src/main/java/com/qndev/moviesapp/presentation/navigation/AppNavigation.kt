package com.qndev.moviesapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qndev.moviesapp.presentation.screens.detail.DetailScreen
import com.qndev.moviesapp.presentation.screens.detail.DetailViewModel
import com.qndev.moviesapp.presentation.screens.movie_list.MovieListScreen
import com.qndev.moviesapp.presentation.screens.movie_list.MovieListViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.background(color = Color.Black),
        navController = navController,
        startDestination = Route.MOVIE_LIST_SCREEN,
    ) {
        composable(Route.MOVIE_LIST_SCREEN) {

            val movieListViewModel = hiltViewModel<MovieListViewModel>()
            val movieListState = movieListViewModel.movieListState.collectAsState().value

            MovieListScreen(
                navController = navController,
                movieListState = movieListState,
                onSearchTextChange = {
                    movieListViewModel.resetMovieListOnQueryChange()
                    movieListViewModel.searchOrGetMovies(it)
                },
                onLoadMore = {
                    movieListViewModel.searchOrGetMovies(it)
                }
            )
        }
        composable(
            route = Route.DETAIL_SCREEN + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {

            val detailViewModel = hiltViewModel<DetailViewModel>()
            val detailState = detailViewModel.detailState.collectAsState().value

            DetailScreen(detailState = detailState)
        }
    }
}
