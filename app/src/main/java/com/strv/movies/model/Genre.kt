package com.strv.movies.model

import com.squareup.moshi.Json

data class Genre(
    val id: Int,
    val name: String
)

data class GenreDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)
