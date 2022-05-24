package com.strv.movies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "revenue") val revenue: Int
)
