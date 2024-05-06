package com.qndev.moviesapp.domain.repository

import com.qndev.moviesapp.domain.model.Movie
import com.qndev.moviesapp.domain.model.MovieDetail
import com.qndev.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies(page: Int): Flow<Resource<List<Movie>>>

    suspend fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>>
}