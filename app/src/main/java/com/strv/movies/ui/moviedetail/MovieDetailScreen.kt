package com.strv.movies.ui.moviedetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.strv.movies.R
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.model.MovieDetail

@Composable
fun MovieDetail(movie: MovieDetail) {
    Column {
        val (videoProgress, setVideoProgress) = rememberSaveable { mutableStateOf(0f) }
        Log.d("TAG", "MovieDetail: $videoProgress")

        MovieTrailerPlayer(
            videoId = OfflineMoviesProvider.getTrailer(movie.id).key,
            progressSeconds = videoProgress,
            setProgress = setVideoProgress
        )

        Row {
            MoviePoster(movie = movie)
            MovieInfo(movie = movie)
        }
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
        Text(movie.releaseDate, modifier = Modifier.padding(top = 8.dp))
        Text(
            movie.overview,
            modifier = Modifier.padding(top = 8.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
    }
}