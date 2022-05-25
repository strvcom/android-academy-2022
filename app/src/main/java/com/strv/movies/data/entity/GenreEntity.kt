package com.strv.movies.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.strv.movies.model.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "genre_id") val genreId: Int,
    @ColumnInfo(name = "name") val name: String
)

fun GenreEntity.toDomain() = Genre(
    id = genreId,
    name = name
)
