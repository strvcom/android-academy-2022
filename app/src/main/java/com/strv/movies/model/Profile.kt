package com.strv.movies.model

import com.squareup.moshi.Json

data class ProfileDTO(
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String
)

data class Profile(
    val name: String,
    val username: String
)

fun ProfileDTO.toProfile(): Profile {
    return Profile(
        name = name,
        username = username
    )
}