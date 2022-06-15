package com.strv.movies.network

import com.strv.movies.network.auth.AuthApi
import com.strv.movies.network.profile.ProfileApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMovieApi(@UnauthorizedRetrofit retrofit: Retrofit): MovieApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthApi(@UnauthorizedRetrofit retrofit: Retrofit): AuthApi = retrofit.create()

    @Provides
    @Singleton
    fun provideProfileApi(@AuthorizedRetrofit retrofit: Retrofit): ProfileApi = retrofit.create()
}