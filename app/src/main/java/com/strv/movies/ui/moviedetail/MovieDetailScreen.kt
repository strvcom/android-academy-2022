package com.strv.movies.ui.moviedetail

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun MovieDetail(movie: MovieDetail) {
    Column {
        MovieTrailerPlayer(
            videoId = OfflineMoviesProvider.getTrailer(movie.id).key,
            progressSeconds = mutableStateOf(0f)
        )

        Row {
            MovieInfo(movie = movie)
            MoviePoster(movie = movie)
        }
    }
}

@Composable
fun MovieTrailerPlayer(videoId: String, progressSeconds: MutableState<Float>) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val configuration = LocalConfiguration.current

    val youTubePlayer = remember(context) {
        YouTubePlayerView(context).apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        youTubePlayer.loadVideo(videoId, progressSeconds.value)
                    } else {
                        youTubePlayer.cueVideo(videoId, progressSeconds.value)
                    }
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    progressSeconds.value = second
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
    Column {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = stringResource(id = R.string.movie_image),
            modifier = Modifier
                .padding(top = 16.dp)
                .size(120.dp),
        )
        Row {
            Text(
                movie.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                color = Color.Red
            )
        }
    }
}

@Composable
fun MovieInfo(movie: MovieDetail) {
    Column {
        Text(
            movie.title,
            modifier = Modifier
                .padding(start = 10.dp, top = 16.dp, end = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            movie.releaseDate,
            modifier = Modifier
                .padding(start = 10.dp, top = 8.dp)
        )
        Text(
            movie.overview,
            modifier = Modifier
                .padding(start = 10.dp, top = 8.dp, end = 10.dp)
                .size(240.dp),
            textAlign = TextAlign.Justify,
            color = Color.Green
        )
    }
}