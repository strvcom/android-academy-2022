package com.strv.movies.data.mapper

import com.strv.movies.data.entity.GenreEntity
import com.strv.movies.data.entity.MovieDetailEntity
import com.strv.movies.data.entity.MovieGenreEntity
import com.strv.movies.model.GenreDTO
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.MovieDetailDTO
import javax.inject.Inject

// Convention is to name a mapper after class of target object.
class MovieDetailMapper @Inject constructor() : Mapper<MovieDetailDTO, MovieDetail> {
    override fun map(from: MovieDetailDTO) =
        MovieDetail(
            id = from.id,
            title = from.title,
            overview = from.overview,
            releaseYear = from.releaseDate.substringBefore("-"), // ideal place to do some small tweaks to data to make it more UI ready
            posterPath = from.posterPath,
            runtime = from.runtime // It would be nice to use string resource here and return formatted string value, sadly we do not have context here.
        )
}

fun MovieDetailDTO.toEntity() = MovieDetailEntity(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    runtime = runtime,
    revenue = revenue
)

fun MovieDetailEntity.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    releaseYear = releaseDate,
    posterPath = posterPath,
    runtime = runtime
)

fun GenreDTO.toEntity() = GenreEntity(
    genreId = id,
    name = name
)

fun GenreDTO.toEntity(movieId: Int) = MovieGenreEntity(
    genreId = id,
    movieId = movieId
)
