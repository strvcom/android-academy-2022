package com.strv.movies.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class RequestTokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "request_token") val token: String
)

@Keep
data class ValidateRequestTokenBody(
    @Json(name ="username") val username: String,
    @Json(name ="password") val password: String,
    @Json(name = "request_token") val requestToken: String
)

@Keep
data class ValidateRequestTokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "request_token") val requestToken: String
)

@Keep
data class CreateSessionBody(
    @Json(name = "request_token") val requestToken: String,
)

@Keep
data class CreateSessionResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "session_id") val sessionToken: String
)

@Keep
data class DeleteSessionBody(
    @Json(name = "session_id") val sessionToken: String
)

@Keep
data class DeleteSessionResponse(
    @Json(name = "success") val isSuccess: Boolean,
)