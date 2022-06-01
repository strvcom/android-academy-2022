package com.strv.movies.model

import com.squareup.moshi.Json

data class RequestTokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "request_token") val token: String
)

data class ValidateRequestTokenBody(
    @Json(name ="username") val username: String,
    @Json(name ="password") val password: String,
    @Json(name = "request_token") val requestToken: String
)

data class ValidateRequestTokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "request_token") val requestToken: String
)

data class CreateSessionBody(
    @Json(name = "request_token") val requestToken: String,
)

data class CreateSessionResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "session_id") val sessionToken: String
)

data class DeleteSessionBody(
    @Json(name = "session_id") val sessionToken: String
)

data class DeleteSessionResponse(
    @Json(name = "success") val isSuccess: Boolean,
)