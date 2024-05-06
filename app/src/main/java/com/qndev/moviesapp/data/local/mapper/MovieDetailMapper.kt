package com.qndev.moviesapp.data.local.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qndev.moviesapp.data.local.detail.BelongsToCollectionEntity
import com.qndev.moviesapp.data.local.detail.GenreEntity
import com.qndev.moviesapp.data.local.detail.MovieDetailEntity
import com.qndev.moviesapp.data.local.detail.ProductionCompanyEntity
import com.qndev.moviesapp.data.local.detail.ProductionCountryEntity
import com.qndev.moviesapp.data.local.detail.SpokenLanguageEntity
import com.qndev.moviesapp.data.remote.api.MovieApi
import com.qndev.moviesapp.data.remote.response.detail.BelongsToCollection
import com.qndev.moviesapp.data.remote.response.detail.Genre
import com.qndev.moviesapp.data.remote.response.detail.MovieDetailDto
import com.qndev.moviesapp.data.remote.response.detail.ProductionCompany
import com.qndev.moviesapp.data.remote.response.detail.ProductionCountry
import com.qndev.moviesapp.data.remote.response.detail.SpokenLanguage
import com.qndev.moviesapp.domain.model.MovieDetail
import javax.inject.Inject

class MovieDetailMapper {

    @Inject
    lateinit var gson: Gson

    @TypeConverter
    fun convertBelongsToCollectionToJson(value: BelongsToCollectionEntity): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToBelongsToCollection(value: String): BelongsToCollectionEntity {
        val objectType = object : TypeToken<BelongsToCollectionEntity>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertGenreEntityListToJson(value: List<GenreEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToGenreEntityList(value: String): List<GenreEntity> {
        val objectType = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertProductionCompanyEntityListToJson(value: List<ProductionCompanyEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToProductionCompanyEntityList(value: String): List<ProductionCompanyEntity> {
        val objectType = object : TypeToken<List<ProductionCompanyEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertProductionCountryEntityListToJson(value: List<ProductionCountryEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToProductionCountryEntityList(value: String): List<ProductionCountryEntity> {
        val objectType = object : TypeToken<List<ProductionCountryEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertSpokenLanguageEntityListToJson(value: List<SpokenLanguageEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToSpokenLanguageEntityList(value: String): List<SpokenLanguageEntity> {
        val objectType = object : TypeToken<List<SpokenLanguageEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertOriginCountryEntityListToJson(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToOriginCountryEntityList(value: String): List<String> {
        val objectType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, objectType)
    }
}

fun MovieDetailDto.toMovieDetailEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        id = id ?: -1,
        adult = adult ?: false,
        backdropPath = backdrop_path ?: "",
        belongsToCollection = belongs_to_collection.toBelongsCollectionEntity(),
        budget = budget ?: -1,
        genres = genres.toGenreEntityList(),
        homepage = homepage ?: "",
        imdbId = imdb_id ?: "",
        originCountry = origin_country ?: emptyList(),
        originalLanguage = original_language ?: "",
        originalTitle = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: -1.0,
        posterPath = poster_path ?: "",
        productionCompanies = production_companies.toProductionCompanyEntityList(),
        productionCountries = production_countries.toProductionCountryEntityList(),
        releaseDate = release_date ?: "",
        revenue = revenue ?: -1,
        runtime = runtime ?: -1,
        spokenLanguages = spoken_languages.toSpokenLanguageEntityList(),
        status = status ?: "",
        tagline = tagline ?: "",
        title = title ?: "",
        video = video ?: false,
        voteAverage = vote_average ?: -1.0,
        voteCount = vote_count ?: -1
    )
}

fun MovieDetailEntity.toMovieDetailModel(): MovieDetail {
    return MovieDetail(
        id = id,
        adult = adult,

        //Add the image url to the path
        backdropPath = MovieApi.IMAGE_URL + backdropPath,
        posterPath = MovieApi.IMAGE_URL + posterPath,

        belongsToCollection = belongsToCollection,
        budget = budget,
        genres = genres,
        homepage = homepage,
        imdbId = imdbId,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

private fun BelongsToCollection?.toBelongsCollectionEntity(): BelongsToCollectionEntity {
    return BelongsToCollectionEntity(
        backdropPath = this?.backdrop_path ?: "",
        id = this?.id ?: -1,
        name = this?.name ?: "",
        posterPath = this?.poster_path ?: ""
    )
}

private fun List<Genre>?.toGenreEntityList(): List<GenreEntity> {
    return this?.map {
        GenreEntity(
            id = it.id,
            name = it.name
        )
    } ?: emptyList()
}

private fun List<ProductionCompany>?.toProductionCompanyEntityList(): List<ProductionCompanyEntity> {
    return this?.map {
        ProductionCompanyEntity(
            id = it.id,
            logoPath = it.logo_path,
            name = it.name,
            originCountry = it.origin_country
        )
    } ?: emptyList()
}

private fun List<ProductionCountry>?.toProductionCountryEntityList(): List<ProductionCountryEntity> {
    return this?.map {
        ProductionCountryEntity(
            iso31661 = it.iso_3166_1,
            name = it.name
        )
    } ?: emptyList()
}

private fun List<SpokenLanguage>?.toSpokenLanguageEntityList(): List<SpokenLanguageEntity> {
    return this?.map {
        SpokenLanguageEntity(
            englishName = it.english_name,
            iso6391 = it.iso_639_1,
            name = it.name
        )
    } ?: emptyList()
}