package com.qndev.moviesapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.qndev.moviesapp.data.local.MovieDatabase
import com.qndev.moviesapp.data.local.movie.MovieDao
import com.qndev.moviesapp.data.local.movie.MovieEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {


    private val timeStamp = System.currentTimeMillis()

    private val mockMovie = MovieEntity(
        backdropPath = "/2KGxQFV9Wp1MshPBf8BuqWUgVAz.jpg",
        id = 940551,
        originalTitle = "Migration",
        overview = "After a migrating duck family alights on their pond with thrilling tales of far-flung places, the Mallard family embarks on a family road trip, from New England, to New York City, to tropical Jamaica.",
        posterPath = "/ldfCF9RhR40mppkzmftxapaHeTo.jpg",
        mediaType = "movie",
        adult = false,
        title = "Migration",
        originalLanguage = "en",
        genreIds = listOf(16, 28, 12, 35, 10751),
        popularity = 654.061,
        releaseDate = "2023-12-06",
        video = false,
        voteAverage = 7.482,
        voteCount = 1298,

        page = 1,
        timeStamp = timeStamp
    )
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        // Create an in-memory database for testing
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getMovieList_empty() {
        // When no movies are inserted
        runBlocking {
            val movies = movieDao.getMovieList(1, timeStamp)
            assertThat(movies, `is`(emptyList()))

        }
    }

    @Test
    fun getMovieList_notEmpty() {
        runBlocking {
            // Given a movie is inserted
            movieDao.upsertMovieList(listOf(mockMovie))

            // When getting the movie list with valid timestamp
            val movies = movieDao.getMovieList(1, timeStamp - 100)

            // Then the result is not empty
            assertThat(movies, `is`(not(emptyList())))
        }
    }

    @Test
    fun getMovieList_expired() {
        runBlocking {
            // Given a movie is inserted
            movieDao.upsertMovieList(listOf(mockMovie))

            // When getting the movie list with an expired timestamp
            val movies = movieDao.getMovieList(1, timeStamp + 100)

            // Then the result is empty
            assertThat(movies, `is`(emptyList()))
        }
    }
}