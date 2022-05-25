package com.strv.movies.data.mapper

import com.strv.movies.model.Trailer
import com.strv.movies.model.TrailerDTO
import javax.inject.Inject

// Convention is to name a mapper after class of target object.
class TrailerMapper @Inject constructor() : Mapper<TrailerDTO, Trailer> {
    override fun map(from: TrailerDTO) =
        Trailer(
            id = from.id,
            name = from.name,
            key = from.key,
            publishedAt = from.publishedAt,
            site = from.site
        )
}