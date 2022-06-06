package com.strv.movies.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.strv.movies.data.entity.GenreEntity
import com.strv.movies.data.entity.MovieGenreEntity

data class Genre(
    val id: Int,
    val name: String
)

@Keep
data class GenreDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)

fun GenreDTO.toEntity() = GenreEntity(
    genreId = id,
    name = name
)

fun GenreDTO.toEntity(movieId: Int) = MovieGenreEntity(
    genreId = id,
    movieId = movieId
)
