package com.strv.movies.model

import androidx.compose.ui.window.isPopupLayout
import com.squareup.moshi.Json
import com.strv.movies.data.entity.MovieEntity
import java.math.RoundingMode

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val popularity: Double
)

data class MovieDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "popularity")
    val popularity: Double
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
    posterPath = posterPath,
    popularity = roundDouble(popularity)
)

private fun roundDouble(input: Double): Double {
    return input.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
}