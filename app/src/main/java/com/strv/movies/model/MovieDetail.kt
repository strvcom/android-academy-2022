package com.strv.movies.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.strv.movies.data.entity.MovieDetailEntity

@Keep
// Used for getting data from network
data class MovieDetailDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "runtime")
    val runtime: Int,
    @Json(name = "genres")
    val genres: List<GenreDTO>
)

// Used for UI
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String?,
    val releaseYear: String,
    val posterPath: String,
    val runtime: Int, // Not used for now - try to include it in UI if you want :)
    val genres: List<Genre>
)

fun MovieDetailDTO.toEntity() = MovieDetailEntity(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    runtime = runtime,
    revenue = revenue
)
