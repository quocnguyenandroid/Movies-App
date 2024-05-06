package com.qndev.moviesapp.data.local.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.qndev.moviesapp.data.local.mapper.MovieDetailMapper

@Entity
data class MovieDetailEntity(
    @PrimaryKey
    val id: Int,

    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: BelongsToCollectionEntity,
    val budget: Int,
    val genres: List<GenreEntity>,
    val homepage: String,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyEntity>,
    val productionCountries: List<ProductionCountryEntity>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageEntity>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

@TypeConverters(MovieDetailMapper::class)
data class BelongsToCollectionEntity(
    val backdropPath: String,
    val id: Int,
    val name: String,
    val posterPath: String
)

@TypeConverters(MovieDetailMapper::class)
data class GenreEntity(
    val id: Int,
    val name: String
)

@TypeConverters(MovieDetailMapper::class)
data class ProductionCompanyEntity(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
)

@TypeConverters(MovieDetailMapper::class)
data class ProductionCountryEntity(
    val iso31661: String,
    val name: String
)

@TypeConverters(MovieDetailMapper::class)
data class SpokenLanguageEntity(
    val englishName: String,
    val iso6391: String,
    val name: String
)

