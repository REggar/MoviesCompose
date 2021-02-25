package com.reggar.moviescompose.movielist.presentation

import com.reggar.moviescompose.movielist.data.model.Movie

sealed class MovieState {
    object Loading: MovieState()

    data class Content(
        val movies: List<Movie>
    ): MovieState()

    object Error: MovieState()
}