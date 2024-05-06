package com.qndev.moviesapp.data.local.detail

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDetailDao {

    @Upsert
    suspend fun upsertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM MovieDetailEntity WHERE id = :id")
    suspend fun getMovieDetail(id: Int): MovieDetailEntity

}