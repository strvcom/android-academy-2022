package com.strv.movies.ui.movieslist

import com.strv.movies.model.Movie

data class MoviesListViewState(
    val movies: List<Movie> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
