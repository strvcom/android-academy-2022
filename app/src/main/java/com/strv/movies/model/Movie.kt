package com.strv.movies.model

import com.squareup.moshi.Json
import com.strv.movies.data.entity.MovieEntity

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String
)

data class MovieDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String
)

// We get data in a way that pagination can be implemented later on :)
data class PopularMoviesDTO(
    @Json(name = "results")
    val results: List<MovieDTO>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)

fun MovieDTO.toEntity() = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath
)