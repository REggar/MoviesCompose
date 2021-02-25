package com.reggar.moviescompose.movielist.domain

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsAll
import assertk.assertions.hasSize
import com.reggar.moviescompose.movielist.data.MovieRepository
import com.reggar.moviescompose.movielist.data.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesUseCaseTest {

    private val starWars =
        Movie(1, "Star Wars", 1977, "Action", "http://sw")
    private val starTrek =
        Movie(2, "Star Trek", 2009, "Action", "http://st")
    private val lordOfTheRings =
        Movie(3, "Lord of the Rings", 2001, "Fantasy", "http://lotr")
    private val movies = listOf(
        starWars,
        starTrek,
        lordOfTheRings,
    )

    private val moviesRepository = mockk<MovieRepository>()
    private val getFilteredMovies = GetMoviesUseCase(moviesRepository)

    @Before
    fun setup() {
        coEvery { moviesRepository.getMovies() } returns movies
    }

    @Test
    fun `getFilteredMoviesUseCase noFilter returnFullList`() = runBlocking {
        assertThat(getFilteredMovies()).all {
            hasSize(3)
            containsAll(starWars, starTrek, lordOfTheRings)
        }
    }

    @Test
    fun `getFilteredMoviesUseCase withFilter filtersByTitle`() = runBlocking {
        assertThat(getFilteredMovies("Star Wa")).all {
            hasSize(1)
            contains(starWars)
        }
    }

    @Test
    fun `getFilteredMoviesUseCase withFilter filtersByGenre`() = runBlocking {
        assertThat(getFilteredMovies("Fantasy")).all {
            hasSize(1)
            contains(lordOfTheRings)
        }
    }
}
