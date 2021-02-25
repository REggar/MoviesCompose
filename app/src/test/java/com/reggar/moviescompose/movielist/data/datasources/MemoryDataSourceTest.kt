package com.reggar.moviescompose.movielist.data.datasources

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.reggar.moviescompose.movielist.data.model.Movie
import io.mockk.mockk
import org.junit.Test
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAmount

class MemoryDataSourceTest {

    private val movies = listOf<Movie>(mockk())
    private val clock = TimeTravelableClock()

    private val memoryDataSource = MemoryDataSource(clock)

    @Test
    fun `getMovies ifEmpty returnsNull`() {
        assertThat(memoryDataSource.getMovies()).isNull()
    }

    @Test
    fun `getMovies ifSetMoviesAndBeforeTimeout returnsMovies`() {
        memoryDataSource.setMovies(movies)
        clock.futureOffset = Duration.of(9, ChronoUnit.MINUTES)

        assertThat(memoryDataSource.getMovies()).isEqualTo(movies)
    }

    @Test
    fun `getMovies ifSetMoviesAndAfterTimeout returnsNull`() {
        memoryDataSource.setMovies(movies)
        clock.futureOffset = Duration.of(11, ChronoUnit.MINUTES)

        assertThat(memoryDataSource.getMovies()).isNull()
    }

    private class TimeTravelableClock : Clock() {
        private val clock = systemDefaultZone()

        var futureOffset: TemporalAmount? = null

        override fun getZone() = clock.zone

        override fun withZone(zone: ZoneId?) = clock.withZone(zone)

        override fun instant(): Instant {
            return if (futureOffset == null) {
                clock.instant()
            } else {
                clock.instant().plus(futureOffset)
            }
        }
    }
}
