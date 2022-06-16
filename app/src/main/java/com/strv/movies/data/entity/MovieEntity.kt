package com.strv.movies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.strv.movies.model.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie") val id: Int,
    @ColumnInfo(name = "movie_title") val title: String,
    @ColumnInfo(name = "movie_poster_path") val posterPath: String
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = posterPath
)