package com.strv.movies.data.mapper

import com.strv.movies.model.Trailer
import com.strv.movies.model.TrailerOnlyDTO
import javax.inject.Inject

class TrailerMapper @Inject constructor() : Mapper<TrailerOnlyDTO, Trailer> {
    override fun map(from: TrailerOnlyDTO) =
        Trailer(
            id = from.id,
            name = from.name,
            key = from.key,
            publishedAt = from.publishedAt,
            site = from.site
        )
}