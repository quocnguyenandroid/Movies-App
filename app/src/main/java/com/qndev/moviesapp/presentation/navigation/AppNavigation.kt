package com.qndev.moviesapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qndev.moviesapp.presentation.screens.detail.DetailScreen
import com.qndev.moviesapp.presentation.screens.movie_list.MovieListScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.background(color = Color.Black),
        navController = navController,
        startDestination = Route.MOVIE_LIST_SCREEN,
    ) {
        composable(Route.MOVIE_LIST_SCREEN) {
            MovieListScreen(navController)
        }
        composable(
            route = Route.DETAIL_SCREEN + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            DetailScreen()
        }
    }
}
