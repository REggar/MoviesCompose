package com.reggar.moviescompose.movielist.data.datasources

import com.reggar.moviescompose.movielist.data.model.Movie
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class MemoryDataSource @Inject constructor(
    private val clock: Clock
) {

    private var movies: List<Movie>? = null
    private var lastFetchedAt: Instant? = null

    fun getMovies(): List<Movie>? {
        val maxCacheAge = clock.instant().minus(10, ChronoUnit.MINUTES)
        if (lastFetchedAt?.isBefore(maxCacheAge) ?: false) {
            movies = null
        }
        return movies
    }

    fun setMovies(newMovies: List<Movie>) {
        movies = newMovies
        lastFetchedAt = clock.instant()
    }
}