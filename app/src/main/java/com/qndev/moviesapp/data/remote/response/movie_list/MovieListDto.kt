package com.qndev.moviesapp.data.remote.response.movie_list

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val page: Int,
    @SerializedName("results")
    val movieList: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)