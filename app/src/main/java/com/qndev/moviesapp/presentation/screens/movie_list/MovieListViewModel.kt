package com.qndev.moviesapp.presentation.screens.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qndev.moviesapp.domain.repository.MovieRepository
import com.qndev.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingMovies()
        }
    }


    private fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieListState.update { it.copy(isLoading = true) }
            movieRepository.getTrendingMovies(movieListState.value.movieListPage)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { movieList ->
                                _movieListState.update {
                                    it.copy(
                                        movieList = movieListState.value.movieList + movieList,
                                        movieListPage = movieListState.value.movieListPage + 1,
                                        errorMassage = ""
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            result.message?.let { msg ->
                                _movieListState.update {
                                    it.copy(isLoading = false, errorMassage = msg)
                                }
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
        }
    }

    fun searchOrGetMovies(query: String) {
        if (query.isEmpty()) {
            getTrendingMovies()
            return
        } else {
            searchMovies(query)
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieRepository.searchMovie(query, movieListState.value.movieListPage)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { movieList ->
                                _movieListState.update {
                                    it.copy(
                                        movieList = movieListState.value.movieList + movieList,
                                        movieListPage = movieListState.value.movieListPage + 1,
                                        errorMassage = ""
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            result.message?.let { msg ->
                                _movieListState.update {
                                    it.copy(isLoading = false, errorMassage = msg)
                                }
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
        }

    }

    fun resetMovieListOnQueryChange() {
        _movieListState.update {
            it.copy(
                movieList = emptyList(),
                movieListPage = 1,
            )
        }
    }

}