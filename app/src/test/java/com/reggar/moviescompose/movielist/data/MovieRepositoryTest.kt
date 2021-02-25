package com.reggar.moviescompose.movielist.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.moviescompose.movielist.data.datasources.MemoryDataSource
import com.reggar.moviescompose.movielist.data.datasources.RemoteDataSource
import com.reggar.moviescompose.movielist.data.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MovieRepositoryTest {

    private val movies = mockk<List<Movie>>()
    private val memoryDataSource = mockk<MemoryDataSource>(relaxUnitFun = true)
    private val remoteDataSource = mockk<RemoteDataSource>()

    private val repository = MovieRepository(memoryDataSource, remoteDataSource)

    @Test
    fun `getMovies ifHasMemoryData returnsMemoryData`() = runBlocking {
        coEvery { memoryDataSource.getMovies() }.returns(movies)

        assertThat(repository.getMovies()).isEqualTo(movies)
    }

    @Test
    fun `getMovies ifNoMemoryData returnsRemoteData`() = runBlocking {
        coEvery { memoryDataSource.getMovies() }.returns(null)
        coEvery { remoteDataSource.getMovies() }.returns(movies)

        assertThat(repository.getMovies()).isEqualTo(movies)
    }

    @Test
    fun `getMovies ifReturnsRemoteData cacheInMemory`() = runBlocking {
        coEvery { memoryDataSource.getMovies() }.returns(null)
        coEvery { remoteDataSource.getMovies() }.returns(movies)

        repository.getMovies()

        verify { memoryDataSource.setMovies(movies) }
    }
}
