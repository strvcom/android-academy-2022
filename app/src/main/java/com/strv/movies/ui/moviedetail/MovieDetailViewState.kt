package com.strv.movies.ui.moviedetail

import com.strv.movies.model.MovieDetailEntity

data class MovieDetailViewState(
    val movie: MovieDetailEntity? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val videoProgress: Float = 0f
)