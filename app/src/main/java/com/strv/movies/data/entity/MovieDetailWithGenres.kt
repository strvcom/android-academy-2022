package com.strv.movies.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieDetailWithGenres(
    @Embedded
    val movie: MovieDetailEntity,

    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(MovieGenreEntity::class)
    )
    val genres: List<GenreEntity>
)
