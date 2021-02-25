package com.reggar.moviescompose.movielist.data

import com.reggar.moviescompose.movielist.data.model.GetMovieResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("/api/movies")
    suspend fun getMovies(): GetMovieResponse
}