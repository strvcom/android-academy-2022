package com.strv.movies.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.OfflineMoviesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableStateFlow(MoviesListViewState(loading = true))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.update {
                MoviesListViewState(
                    movies = OfflineMoviesProvider.getMovies()
                )
            }
        }
    }
}