package com.reggar.moviescompose.movielist.presentation

import androidx.lifecycle.viewModelScope
import com.reggar.moviescompose.common.mvi.BaseMviViewModel
import com.reggar.moviescompose.movielist.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase
) : BaseMviViewModel<MovieState, MovieEvent>(MovieState.Loading) {

    init {
         onEvent(MovieEvent.OnStart)
    }

    override fun onEvent(event: MovieEvent) = when (event) {
        MovieEvent.OnStart -> {
            loadMovies()
        }
        is MovieEvent.OnSearchChanged -> {
            loadMovies(event.searchTerm)
        }
    }

    private fun loadMovies(searchTerm: String = "") {
        setState(MovieState.Loading)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val movies = getMovies(
                    filterBy = searchTerm
                )
                setState(MovieState.Content(movies))
            } catch (exception: Exception) {
                setState(MovieState.Error)
            }
        }
    }
}

