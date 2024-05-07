package com.qndev.moviesapp.data.remote.api

import com.qndev.moviesapp.data.remote.response.detail.MovieDetailDto
import com.qndev.moviesapp.data.remote.response.movie_list.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): MovieListDto

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): MovieListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        const val IMDB_URL = "https://www.imdb.com/title/"
        const val API_KEY = "47aa75b56464da7a186b813a50035cd4"
    }
}