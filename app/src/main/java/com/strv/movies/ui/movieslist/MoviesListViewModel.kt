package com.strv.movies.ui.movieslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _viewState = mutableStateOf(MoviesListViewState(loading = true))
    val viewState = _viewState

    init {
        observePopularMovies()
    }

    private fun observePopularMovies() {
    _viewState.value = viewState.value.copy(loading = true)
        viewModelScope.launch {
            movieRepository.fetchPopularMovies(true).collect { response ->
                response.fold(
                    { error ->
                        Log.d("TAG", "PopularMovies Error: $error")
                        _viewState.value = viewState.value.copy(error = error, loading = false)
                    },
                    { list ->
                        _viewState.value = viewState.value.copy(movies = list, loading = false)
                    }
                )
            }
        }
    }

}