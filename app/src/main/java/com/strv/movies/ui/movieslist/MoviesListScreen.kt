package com.strv.movies.ui.movieslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
        contentDescription = stringResource(id = R.string.movie_image)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(movies: List<Movie>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}