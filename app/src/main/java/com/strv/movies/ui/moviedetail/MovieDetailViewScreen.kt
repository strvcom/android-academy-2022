package com.strv.movies.ui.moviedetail

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.databinding.MovieDetailBinding
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MovieDetailViewScreen(
    viewModel: MovieDetailViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState(MovieDetailViewState(loading = true))
    val configuration = LocalConfiguration.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val useDarkTheme = !MaterialTheme.colors.isLight

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        AndroidViewBinding(factory = MovieDetailBinding::inflate) {
            textTitle.text = viewState.movie?.title
            textTitle.setTextColor(if (useDarkTheme) Color.WHITE else Color.BLACK)

            textDescription.setContent {
                Text(
                    viewState.movie?.overview ?: "",
                    modifier = Modifier.padding(top = 8.dp, start = 20.dp, end = 20.dp),
                    textAlign = TextAlign.Justify
                )
            }

            setupYoutubePlayer(
                youtubePlayer,
                viewState,
                configuration,
                lifecycle,
                viewModel::updateVideoProgress
            )

            imageCover.load("https://image.tmdb.org/t/p/w500${viewState.movie?.posterPath}")

            recyclerLabels.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerLabels.adapter =
                LabelsAdapter(viewState.movie?.genres?.map { it.name } ?: emptyList())
        }
    }
}

private fun setupYoutubePlayer(
    youtubePlayer: YouTubePlayerView,
    viewState: MovieDetailViewState,
    configuration: Configuration,
    lifecycle: Lifecycle,
    setVideoProgress: (second: Float) -> Unit,
) {
    youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                youTubePlayer.loadVideo(
                    OfflineMoviesProvider.getTrailer(viewState.movie!!.id).key,
                    viewState.videoProgress
                )
            } else {
                youTubePlayer.cueVideo(
                    OfflineMoviesProvider.getTrailer(viewState.movie!!.id).key,
                    viewState.videoProgress
                )
            }
        }

        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            setVideoProgress(second)
        }
    })

    lifecycle.addObserver(youtubePlayer)
}