package com.strv.movies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_genre", primaryKeys = ["genre_id", "movie_id"])
data class MovieGenreEntity(
    @ColumnInfo(name = "genre_id") val genreId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
)
