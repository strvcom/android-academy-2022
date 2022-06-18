package com.strv.movies.model

import com.squareup.moshi.Json

data class TrailerListDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "results") val trailers: List<TrailerDTO>
)

data class TrailerDTO(
    @Json(name = "name") val name: String,
    @Json(name = "key") val key: String,
    @Json(name = "published_at") val publishedAt: String
)

data class Trailer(
    val key: String
)

fun TrailerDTO.toDomain(): Trailer = Trailer(
    key = key
)