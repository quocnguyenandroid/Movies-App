package com.qndev.moviesapp.domain.model

import com.qndev.moviesapp.data.local.detail.BelongsToCollectionEntity
import com.qndev.moviesapp.data.local.detail.GenreEntity
import com.qndev.moviesapp.data.local.detail.ProductionCompanyEntity
import com.qndev.moviesapp.data.local.detail.ProductionCountryEntity
import com.qndev.moviesapp.data.local.detail.SpokenLanguageEntity

data class MovieDetail(
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
    val voteCount: Int,

    val imdbUrl: String
)
