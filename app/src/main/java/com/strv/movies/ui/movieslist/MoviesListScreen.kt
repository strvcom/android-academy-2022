package com.strv.movies.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.strv.movies.R
import com.strv.movies.model.Movie
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MoviesListScreen(
    navigateToMovieDetail: (movieId: Int) -> Unit,
    viewModel: MoviesListViewModel = viewModel()
) {
    val viewState by viewModel.viewState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewState.isRefreshing
    )
    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        MoviesList(
            movies = viewState.movies,
            onMovieClick = navigateToMovieDetail,
            refreshState = swipeRefreshState,
            onRefresh = { viewModel.refreshData() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MoviesList(
    movies: List<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
    refreshState: SwipeRefreshState,
    onRefresh: () -> Unit
) {
    SwipeRefresh(state = refreshState,
        onRefresh = { onRefresh() }) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
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
}

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
        Row(
            modifier = Modifier
                .wrapContentWidth(align = Alignment.Start)
                .padding(4.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .alpha(0.9f)
                .background(MaterialTheme.colors.primary)
        ) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(2.dp),
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.movies_list_popularity_icon)
            )
            Text(
                text = movie.popularity.dec().toString(),
                maxLines = 1,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .align(CenterVertically)
            )
        }
    }
}

