package com.strv.movies.ui.moviedetail

import com.strv.movies.model.MovieDetail

data class MovieDetailViewState(
    val movie: MovieDetail? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val videoProgress: Float = 0f
)