package com.qndev.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qndev.moviesapp.data.remote.api.MovieApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieApi: MovieApi
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieApi.getTrendingMovies()
        }
    }

    fun init() {

    }

}