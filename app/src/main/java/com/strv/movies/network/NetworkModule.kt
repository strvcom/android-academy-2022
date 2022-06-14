package com.strv.movies.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.strv.movies.BuildConfig
import com.strv.movies.network.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UnauthorizedOkHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthorizedOkHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthorizedRetrofit

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UnauthorizedRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val REQUEST_TIMEOUT_SEC = 30L
    private const val MAX_PARALLEL_REQUESTS = 5
    private const val BASE_URL: String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    @UnauthorizedOkHttpClient
    fun provideUnauthorizedRetrofitOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // Setup Timeouts
            connectTimeout(REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)

            // Setup Max Requests
            dispatcher(Dispatcher().apply { maxRequests = MAX_PARALLEL_REQUESTS })
            retryOnConnectionFailure(false)

            // Setup Interceptors
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(getQueryInterceptor())
        }.build()
    }

    @Provides
    @Singleton
    @AuthorizedOkHttpClient
    fun provideAuthorizeRetrofitOkHttpClient(
        @UnauthorizedOkHttpClient okHttpClient: OkHttpClient,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return okHttpClient.newBuilder().apply {
            addInterceptor(authInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("HttpLoggingInterceptor", message)
    }.apply {
        level = when (BuildConfig.BUILD_TYPE == "debug") {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    @UnauthorizedRetrofit
    fun provideUnauthorizedRetrofit(@UnauthorizedOkHttpClient okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    @AuthorizedRetrofit
    fun provideAuthorizedRetrofit(@UnauthorizedRetrofit retrofit: Retrofit, @AuthorizedOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        retrofit.newBuilder().client(okHttpClient).build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Need to be set as a last adapter
        .build()

    private fun getQueryInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", "682d746760d6c45f22b0270b8f953642")
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }
}
