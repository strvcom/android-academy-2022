package com.strv.movies.ui.moviedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.MovieRepository
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    movieRepository: MovieRepository
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie id is missing..."
        }

    private val _viewState = MutableStateFlow(MovieDetailViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getMovieDetail(movieId).fold({
                error ->
                Log.d("TAG", "MovieDetailLoadingError: $error")
                _viewState.update {
                    MovieDetailViewState(
                        error = error
                    )
                }
            }, { movie ->
                Log.d("TAG", "MovieDetailSuccess: ${movie.title}")
                _viewState.update {
                    MovieDetailViewState(
                        movie = movie
                    )
                }
            })
        }
    }

    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}