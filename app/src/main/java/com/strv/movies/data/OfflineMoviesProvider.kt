package com.strv.movies.data

import com.strv.movies.model.*

object OfflineMoviesProvider {
    fun getTrailer(movieId: Int): Trailer {
        return Trailer(
            "en",
            "US",
            "Official Trailer",
            "JfVOs4VSpmA",
            "YouTube",
            1080,
            "Trailer",
            true,
            "2021-11-17T01:30:05.000Z",
            "61945b8a4da3d4002992d5a6"
        )
    }
}