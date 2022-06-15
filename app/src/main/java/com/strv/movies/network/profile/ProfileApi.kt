package com.strv.movies.network.profile

import com.strv.movies.model.ProfileDTO
import retrofit2.http.GET

interface ProfileApi {
    @GET("account")
    suspend fun getAccountDetails(): ProfileDTO
}