package com.reggar.moviescompose.movielist.data.datasources

import com.reggar.moviescompose.movielist.data.MovieApi
import com.reggar.moviescompose.movielist.data.model.Movie
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getMovies(): List<Movie> {
        return movieApi.getMovies().data
    }
}