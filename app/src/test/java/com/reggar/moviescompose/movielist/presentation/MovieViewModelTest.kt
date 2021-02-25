package com.reggar.moviescompose.movielist.presentation

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.moviescompose.common.MainCoroutineRule
import com.reggar.moviescompose.common.neverReturns
import com.reggar.moviescompose.movielist.domain.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class MovieViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getMoviesUseCase = mockk<GetMoviesUseCase>()

    private val viewModel by lazy {
        MovieViewModel(getMoviesUseCase)
    }

    @Test
    fun `onEmpty emitLoadingState`() {
        assertLatestState().isEqualTo(MovieState.Loading)
    }

    @Test
    fun `onStart whileAwaitGetMovies emitLoadingState`() {
        coEvery { getMoviesUseCase() }.neverReturns()

        viewModel.onEvent(MovieEvent.OnStart)

        assertLatestState().isEqualTo(MovieState.Loading)
    }

    @Test
    fun `onStart onSuccessfulGetMovies emitContentState`() {
        coEvery { getMoviesUseCase() }.returns(listOf())

        viewModel.onEvent(MovieEvent.OnStart)

        assertLatestState().isEqualTo(MovieState.Content(emptyList()))
    }

    @Test
    fun `onSearchChanged whileAwaitGetMovies LoadingState`() {
        val searchTerm = "Action"

        coEvery { getMoviesUseCase(searchTerm) }.neverReturns()

        viewModel.onEvent(MovieEvent.OnSearchChanged(searchTerm))

        assertLatestState().isEqualTo(MovieState.Loading)
    }

    @Test
    fun `onSearchChanged onSuccessfulGetMovies ContentState`() {
        val searchTerm = "Action"

        coEvery { getMoviesUseCase(searchTerm) }.returns(listOf())

        viewModel.onEvent(MovieEvent.OnSearchChanged(searchTerm))

        assertLatestState().isEqualTo(MovieState.Content(emptyList()))
    }

    private fun assertLatestState() = assertThat(viewModel.state.value)
}
