package com.strv.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.data.database.MoviesDataStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val moviesDataStore: MoviesDataStore,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface MainViewModelFactory {
        fun create(handle: SavedStateHandle): MainViewModel
    }

    val isDarkTheme = moviesDataStore.darkModeFlow

    fun changeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            moviesDataStore.setDarkTheme(isDarkTheme)
        }
    }
}
