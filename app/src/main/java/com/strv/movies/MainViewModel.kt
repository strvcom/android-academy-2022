package com.strv.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.database.AuthDataStore
import com.strv.movies.database.MoviesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesDataStore: MoviesDataStore,
    val authDataStore: AuthDataStore,
) : ViewModel() {

    val isDarkTheme = moviesDataStore.darkModeFlow

    fun changeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            moviesDataStore.setDarkTheme(isDarkTheme)
        }
    }
}
