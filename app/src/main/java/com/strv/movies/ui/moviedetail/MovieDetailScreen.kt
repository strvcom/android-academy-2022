package com.strv.movies.ui.moviedetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.strv.movies.R
import com.strv.movies.model.Genre
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.Trailer
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState(MovieDetailViewState(loading = true))
    Log.d("TAG", "DetailScreeen state: $viewState")

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        viewState.movie?.let {
            MovieDetail(
                movie = it,
                videoProgress = viewState.videoProgress,
                setVideoProgress = viewModel::updateVideoProgress,
                trailer = viewState.trailer
            )
        }
    }
}

@Composable
fun MovieDetail(
    movie: MovieDetail,
    trailer: Trailer?,
    videoProgress: Float = 0f,
    setVideoProgress: (second: Float) -> Unit
) {
    Column {
        Log.d("TAG", "MovieDetail: $videoProgress")
        if (trailer != null) {
            MovieTrailerPlayer(
                videoId = trailer.key,
                progressSeconds = videoProgress,
                setProgress = setVideoProgress
            )
        }

        Row {
            MoviePoster(movie = movie)
            MovieInfo(movie = movie)
        }

        GenresList(genres = movie.genres)
    }
}

@Composable
fun MovieTrailerPlayer(
    videoId: String,
    progressSeconds: Float,
    setProgress: (second: Float) -> Unit
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val configuration = LocalConfiguration.current

    val youTubePlayer = remember(context) {
        YouTubePlayerView(context).apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        youTubePlayer.loadVideo(videoId, progressSeconds)
                    } else {
                        youTubePlayer.cueVideo(videoId, progressSeconds)
                    }
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    setProgress(second)
                }
            })
        }
    }

    lifecycle.addObserver(youTubePlayer)

    // Gateway to traditional Android Views
    AndroidView(
        factory = { youTubePlayer }
    )
}

@Composable
fun MoviePoster(movie: MovieDetail) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
        contentDescription = stringResource(id = R.string.movie_image),
        modifier = Modifier
            .padding(top = 16.dp)
            .size(120.dp)
    )
}

@Composable
fun MovieInfo(movie: MovieDetail) {
    Column {
        Text(
            movie.title,
            modifier = Modifier.padding(top = 16.dp, end = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(movie.releaseYear, modifier = Modifier.padding(top = 8.dp))
        movie.overview?.let { overview ->
            Text(
                overview,
                modifier = Modifier.padding(top = 8.dp, end = 16.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun GenresList(genres: List<Genre>) {
    LazyRow {
        itemsIndexed(items = genres) { _, item ->
            Text(
                text = item.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
