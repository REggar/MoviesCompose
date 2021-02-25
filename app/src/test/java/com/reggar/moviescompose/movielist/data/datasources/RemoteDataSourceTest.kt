package com.reggar.moviescompose.movielist.data.datasources

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.moviescompose.movielist.data.MovieApi
import com.reggar.moviescompose.movielist.data.model.GetMovieResponse
import com.reggar.moviescompose.movielist.data.model.Movie
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoteDataSourceTest {

    private val movies = listOf<Movie>(mockk())
    private val getMovieResponse = mockk<GetMovieResponse> {
        every { data } returns movies
    }
    private val movieApi = mockk<MovieApi>()

    private val remoteDataSource = RemoteDataSource(movieApi)

    @Test
    fun `getMovies ifHasMemoryData returnsMemoryData`() = runBlocking {
        coEvery { movieApi.getMovies() }.returns(getMovieResponse)

        assertThat(remoteDataSource.getMovies()).isEqualTo(movies)
    }
}
