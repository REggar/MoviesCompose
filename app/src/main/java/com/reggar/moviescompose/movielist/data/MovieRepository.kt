package com.reggar.moviescompose.movielist.data

import com.reggar.moviescompose.movielist.data.datasources.MemoryDataSource
import com.reggar.moviescompose.movielist.data.datasources.RemoteDataSource
import com.reggar.moviescompose.movielist.data.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val memoryDataSource: MemoryDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getMovies(): List<Movie> {
        val cachedMovies = memoryDataSource.getMovies()
        if (cachedMovies != null) {
            return cachedMovies
        }
        val freshMovies = remoteDataSource.getMovies()
        memoryDataSource.setMovies(freshMovies)
        return freshMovies
    }
}