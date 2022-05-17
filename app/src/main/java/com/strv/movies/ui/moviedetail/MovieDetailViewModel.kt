package com.strv.movies.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie id is missing..."
        }

    private val _viewState = MutableStateFlow(MovieDetailViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.update {
                val randomNumber = Random.nextInt(10)
                if (randomNumber < 3) {
                    MovieDetailViewState(error = "Something went wrong!")
                } else {
                    MovieDetailViewState(
                        movie = OfflineMoviesProvider.getMovieDetail(movieId)
                    )
                }
            }
        }
    }

    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}