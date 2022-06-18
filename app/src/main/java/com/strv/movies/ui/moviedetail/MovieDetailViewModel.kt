package com.strv.movies.ui.moviedetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.Trailer
import com.strv.movies.network.MovieRepository
import com.strv.movies.ui.navigation.MoviesNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId =
        requireNotNull(savedStateHandle.get<Int>(MoviesNavArguments.MOVIE_ID_KEY)) {
            "Movie id is missing..."
        }

    private val _viewState = MutableStateFlow(MovieDetailViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        observeMovieDetail()

        viewModelScope.launch {
            movieRepository.fetchMovieDetail(movieId).fold(
                { error ->
                    Log.d("TAG", "MovieDetailLoadingError: $error")
                    _viewState.update {
                        MovieDetailViewState(
                            error = error
                        )
                    }
                },
                { movieTitle ->
                    Log.d("TAG", "MovieDetailLoaded: $movieTitle")
                }
            )
        }
    }


    private fun observeMovieDetail() {
        viewModelScope.launch {
            Log.d("TAG", "ViewModel: Observing values")

            val movieTrailerDeferred = async {
                movieRepository.fetchMovieTrailer(movieId).fold(
                    { error ->
                        Log.d("TAG", "ViewModel MovieTrailer Error")
                        _viewState.update {
                            MovieDetailViewState(error = error)
                        }
                    },
                    {
                        Log.d("TAG", "ViewModel MovieTrailer Success $it")
//                    _movieTrailer.value?.copy(key = it.key)
                        _viewState.value =
                            _viewState.value.copy(trailer = it, loading = false, error = null)
                    }
                )
            }
            val movieDetailDeferred = async {
                movieRepository.observeMovieDetail(movieId).collect { detail ->
                    Log.d("TAG", "ViewModel MovieDetail collected $detail")
//                _movieDetail.value = detail
                    _viewState.value =
                        _viewState.value.copy(movie = detail, loading = false, error = null)
                }
            }
            movieTrailerDeferred.await()
            movieDetailDeferred.await()
        }
    }


    fun updateVideoProgress(progress: Float) {
        _viewState.update { it.copy(videoProgress = progress) }
    }
}
