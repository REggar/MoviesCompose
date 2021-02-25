package com.reggar.moviescompose.movielist.domain

import com.reggar.moviescompose.movielist.data.MovieRepository
import com.reggar.moviescompose.movielist.data.model.Movie
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(
        filterBy: String = ""
    ): List<Movie> {
        val unfilteredMovies = movieRepository.getMovies()
        return unfilteredMovies.filter { movie ->
            movie.title.contains(filterBy, ignoreCase = true) ||
                    movie.genre.contains(filterBy, ignoreCase = true)
        }
    }
}