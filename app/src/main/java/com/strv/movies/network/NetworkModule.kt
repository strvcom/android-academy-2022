package com.strv.movies.network

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.strv.movies.BuildConfig
import com.strv.movies.R
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
import retrofit2.create
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create()

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val REQUEST_TIMEOUT_SEC = 30L
    private const val MAX_PARALLEL_REQUESTS = 5
    private const val BASE_URL: String = "https://api.themoviedb.org/3/"


    @Provides
    @Singleton
    fun provideRetrofitOkHttpClient(
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
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Need to be set as a last adapter
        .build()

    private fun getQueryInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", "725b8af480d381b917131b58191acc14") // TODO ADD your key
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }
}
