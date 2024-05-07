package com.qndev.moviesapp.data.repositoty

import com.qndev.moviesapp.data.local.MovieDatabase
import com.qndev.moviesapp.data.local.mapper.toMovieDetailEntity
import com.qndev.moviesapp.data.local.mapper.toMovieDetailModel
import com.qndev.moviesapp.data.local.mapper.toMovieEntity
import com.qndev.moviesapp.data.local.mapper.toMovieModel
import com.qndev.moviesapp.data.local.movie.MovieEntity
import com.qndev.moviesapp.data.remote.api.MovieApi
import com.qndev.moviesapp.domain.model.Movie
import com.qndev.moviesapp.domain.model.MovieDetail
import com.qndev.moviesapp.domain.repository.MovieRepository
import com.qndev.moviesapp.util.Resource
import com.qndev.moviesapp.util.expireTime
import com.qndev.moviesapp.util.now
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieRepository {
    override suspend fun getTrendingMovies(page: Int): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val trendingMovies = try {
                movieApi.getTrendingMovies(page = page)
            } catch (e: Exception) {
                e.printStackTrace()

                // Get movie list from database if error from Api
                val localMovieList = getMovieListFromDatabase(page)
                if (localMovieList.isEmpty()) {
                    emit(Resource.Error(message = "No data to be shown"))
                } else {
                    emit(Resource.Success(data = localMovieList.map { it.toMovieModel() }))

                }
                emit(Resource.Loading(false))
                return@flow
            }

            // Map trendingMovies to List<MovieEntity> if success from Api
            val movieEntities = trendingMovies.movieList.map { movieDto ->
                movieDto.toMovieEntity(timeStamp = now, page = page)
            }

            // Save movie list to database
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(data = movieEntities.map { it.toMovieModel() }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            val searchResultMovies = try {
                movieApi.searchMovie(query = query, page = page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error while searching"))
                return@flow
            }

            if (searchResultMovies.movieList.isNotEmpty()) {
                emit(Resource.Success(data = searchResultMovies.movieList.map { it.toMovieModel() }))
            } else {
                emit(Resource.Error(message = "No results found"))
            }
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {

            emit(Resource.Loading(true))

            val movieDetails = try {
                movieApi.getMovieDetails(movieId = movieId)
            } catch (e: Exception) {
                e.printStackTrace()

                // Get movie details from database if error from Api
                val localMovieDetails = movieDatabase.movieDetailDao.getMovieDetail(movieId)
                if (localMovieDetails != null) { // Database will return null if movie not found
                    emit(Resource.Success(data = localMovieDetails.toMovieDetailModel()))
                } else {
                    emit(Resource.Error(message = "Something went wrong"))
                }
                emit(Resource.Loading(false))
                return@flow
            }

            // Map movieDetails to MovieDetailEntity if success from Api
            val movieDetailEntity = movieDetails.toMovieDetailEntity()

            // Save movie details to database
            movieDatabase.movieDetailDao.upsertMovieDetail(movieDetailEntity)

            emit(Resource.Success(data = movieDetailEntity.toMovieDetailModel()))
            emit(Resource.Loading(false))
        }
    }

    private suspend fun getMovieListFromDatabase(page: Int): List<MovieEntity> {
        // Delete expired movies before fetching from database
        movieDatabase.movieDao.deleteExpiredMovies(now - expireTime)
        val movieList = movieDatabase.movieDao.getMovieList(page)
        return movieList
    }
}