package com.strv.movies.ui.movieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.model.Movie
import com.strv.movies.network.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(MoviesListViewState(loading = true))
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())

    val viewState = combine(_viewState, _movies) { state, movies ->
        when {
            movies.isNotEmpty() -> MoviesListViewState(movies = movies)
            else -> state
        }
    }

    init {
        observePopularMovies()

        viewModelScope.launch {
            fetchPopularMovies()
        }
    }

    private suspend fun fetchPopularMovies() {
        Log.e("TAG", "MovieList - Start fetching data.")
        movieRepository.fetchPopularMovies().fold(
            { error ->
                Log.d("TAG", "MovieListLoadingError: $error")
                _viewState.update {
                    MoviesListViewState(
                        error = error
                    )
                }
            },
            { updatedMoviesCount ->
                Log.e("TAG", "MovieListSuccess: $updatedMoviesCount")
            }
        )
    }

    private fun observePopularMovies() {
        viewModelScope.launch {
            movieRepository.observePopularMovies().collect {
                Log.d("TAG", "observePopularMovies: ${it.size}")
                _movies.value = it
            }
        }
    }

}
