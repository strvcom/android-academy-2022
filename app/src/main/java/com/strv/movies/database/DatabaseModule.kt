package com.strv.movies.database

import android.content.Context
import androidx.room.Room
import com.strv.movies.data.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext conntext: Context): MoviesDatabase =
        Room.databaseBuilder(
            conntext,
            MoviesDatabase::class.java,
            MoviesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao = database.getMoviesDao()
}
