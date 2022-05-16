package com.strv.movies.network

import com.strv.movies.model.MovieDetailEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailEntity
}