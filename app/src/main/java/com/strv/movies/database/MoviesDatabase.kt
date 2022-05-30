package com.strv.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.strv.movies.data.dao.MoviesDao
import com.strv.movies.data.entity.GenreEntity
import com.strv.movies.data.entity.MovieDetailEntity
import com.strv.movies.data.entity.MovieEntity
import com.strv.movies.data.entity.MovieGenreEntity
import com.strv.movies.database.MoviesDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [
        MovieDetailEntity::class, GenreEntity::class, MovieGenreEntity::class, MovieEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movies_database"
        const val DATABASE_VERSION = 2
    }

    abstract fun getMoviesDao(): MoviesDao
}
