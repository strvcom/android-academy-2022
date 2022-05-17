package com.strv.movies.ui.login

import com.strv.movies.model.Movie

data class MoviesLoginViewState(
    val movies: List<Movie> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)
