package com.reggar.moviescompose.movielist.presentation

sealed class MovieEvent {
    object OnStart : MovieEvent()

    data class OnSearchChanged(
        val searchTerm: String
    ) : MovieEvent()
}