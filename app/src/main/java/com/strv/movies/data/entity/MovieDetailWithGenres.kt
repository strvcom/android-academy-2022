package com.strv.movies.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.strv.movies.model.MovieDetail

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

fun MovieDetailWithGenres.toDomain() = MovieDetail(
    id = movie.id,
    title = movie.title,
    overview = movie.overview,
    posterPath = movie.posterPath,
    releaseYear = movie.releaseDate,
    runtime = movie.runtime,
    genres = genres.map { it.toDomain() }
)
