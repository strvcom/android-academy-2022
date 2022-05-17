package com.strv.movies.data.mapper

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
            posterPath = from.posterPath
        )
}