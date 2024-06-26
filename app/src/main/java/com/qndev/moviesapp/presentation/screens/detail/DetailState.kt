package com.qndev.moviesapp.presentation.screens.detail

import com.qndev.moviesapp.domain.model.MovieDetail

data class DetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val errorMessage: String = ""
)