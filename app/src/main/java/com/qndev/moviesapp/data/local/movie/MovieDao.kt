package com.qndev.moviesapp.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE page = :page")
    suspend fun getMovieList(page: Int): List<MovieEntity>

    @Query(
        """
        DELETE FROM MovieEntity
        WHERE timeStamp <= :timeStamp
    """
    )
    suspend fun deleteExpiredMovies(timeStamp: Long)
}