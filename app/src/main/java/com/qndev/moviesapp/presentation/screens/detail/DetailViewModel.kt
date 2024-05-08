package com.qndev.moviesapp.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId") ?: -1

    private var _detailsState = MutableStateFlow(DetailsState())
    val detailState = _detailsState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetails(movieId)
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailsState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovieDetails(movieId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movieDetail ->
                            _detailsState.update { it.copy(movieDetail = movieDetail) }
                        }
                    }

                    is Resource.Error -> {
                        result.message?.let { msg ->
                            _detailsState.update { it.copy(errorMessage = msg) }
                        }
                    }

                    is Resource.Loading -> {
                        _detailsState.update { it.copy(isLoading = result.isLoading) }
                    }
                }
            }
        }
    }
}