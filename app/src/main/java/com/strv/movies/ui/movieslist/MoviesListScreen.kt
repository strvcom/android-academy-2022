package com.strv.movies.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.model.Movie

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(all = 8.dp)
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            model = "https://image.tmdb.org/t/p/h632${movie.posterPath}",
            contentDescription = stringResource(id = R.string.movie_image)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MoviesList(
    movies: List<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        cells = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->
            val state = remember {
                MutableTransitionState(false).apply {
                    // Start the animation immediately.
                    targetState = true
                }
            }
            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300))
            ) {
                MovieItem(
                    movie = movie,
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable { onMovieClick(movie.id) }
                )
            }
        }
    }
}
