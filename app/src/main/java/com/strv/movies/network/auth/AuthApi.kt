package com.strv.movies.network.auth

import com.strv.movies.model.CreateSessionBody
import com.strv.movies.model.CreateSessionResponse
import com.strv.movies.model.DeleteSessionBody
import com.strv.movies.model.DeleteSessionResponse
import com.strv.movies.model.ValidateRequestTokenBody
import com.strv.movies.model.RequestTokenResponse
import com.strv.movies.model.ValidateRequestTokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface AuthApi {
    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestToken(@Body body: ValidateRequestTokenBody): ValidateRequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: CreateSessionBody): CreateSessionResponse

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(@Body body: DeleteSessionBody): DeleteSessionResponse
}