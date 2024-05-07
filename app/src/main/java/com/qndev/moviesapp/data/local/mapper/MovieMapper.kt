package com.qndev.moviesapp.data.local.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qndev.moviesapp.data.local.movie.MovieEntity
import com.qndev.moviesapp.data.remote.api.MovieApi
import com.qndev.moviesapp.data.remote.response.movie_list.MovieDto
import com.qndev.moviesapp.domain.model.Movie

class MovieMapper {

    private val gson = Gson()

    @TypeConverter
    fun convertGenreListToJson(value: List<Int>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToGenreList(value: String): List<Int> {
        val objectType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, objectType)
    }
}

fun MovieDto.toMovieEntity(
    timeStamp: Long,
    page: Int,
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        id = id ?: -1,
        originalTitle = originalTitle ?: "",
        video = video ?: false,
        genreIds = genreIds ?: listOf(-1, -2),
        mediaType = mediaType ?: "",
        timeStamp = timeStamp,
        page = page,
    )
}

fun MovieEntity.toMovieModel(): Movie {
    return Movie(
        adult = adult,

        //Add the image url to the path
        backdropPath = MovieApi.IMAGE_URL + backdropPath,
        posterPath = MovieApi.IMAGE_URL + posterPath,

        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        id = id,
        originalTitle = originalTitle,
        video = video,
        genreIds = genreIds,
        mediaType = mediaType,
    )
}

fun MovieDto.toMovieModel(): Movie {
    return Movie(
        adult = adult ?: false,

        //Add the image url to the path
        backdropPath = MovieApi.IMAGE_URL + backdropPath,
        posterPath = MovieApi.IMAGE_URL + posterPath,

        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        id = id ?: -1,
        originalTitle = originalTitle ?: "",
        video = video ?: false,
        genreIds = genreIds ?: listOf(-1, -2),
        mediaType = mediaType ?: "",
    )
}