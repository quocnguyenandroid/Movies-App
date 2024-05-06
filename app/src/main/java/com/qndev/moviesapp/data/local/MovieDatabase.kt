package com.qndev.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qndev.moviesapp.data.local.detail.MovieDetailDao
import com.qndev.moviesapp.data.local.detail.MovieDetailEntity
import com.qndev.moviesapp.data.local.mapper.MovieDetailMapper
import com.qndev.moviesapp.data.local.mapper.MovieMapper
import com.qndev.moviesapp.data.local.movie.MovieDao
import com.qndev.moviesapp.data.local.movie.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailEntity::class],
    version = 1
)
@TypeConverters(MovieDetailMapper::class, MovieMapper::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val movieDetailDao: MovieDetailDao
}