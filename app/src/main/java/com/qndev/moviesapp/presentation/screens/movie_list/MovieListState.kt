package com.qndev.moviesapp.presentation.screens.movie_list

import com.qndev.moviesapp.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movieListPage: Int = 1,
    val movieList: List<Movie> = emptyList(),
    val errorMassage: String = ""
)
