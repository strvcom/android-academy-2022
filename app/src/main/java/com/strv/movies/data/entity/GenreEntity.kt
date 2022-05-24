package com.strv.movies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "genre_id") val genreId: Int,
    @ColumnInfo(name = "name") val name: String
)
