package com.strv.movies.ui.moviedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.databinding.MovieDetailBinding
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MovieDetailViewScreen(
    viewModel: MovieDetailViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState(MovieDetailViewState(loading = true))

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        AndroidViewBinding(factory = MovieDetailBinding::inflate) {
            textTitle.text = viewState.movie?.title
        }
    }
}
