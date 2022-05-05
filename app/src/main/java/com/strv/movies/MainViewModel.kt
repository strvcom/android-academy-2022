package com.strv.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    @Assisted isDarkTheme: Boolean,
): ViewModel() {

    @AssistedFactory
    interface MainViewModelFactory {
        fun create(handle: SavedStateHandle, isDarkTheme: Boolean): MainViewModel
    }

    private var _isDarkTheme = MutableStateFlow(isDarkTheme)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun changeTheme(isDarkTheme: Boolean) {
        _isDarkTheme.update { isDarkTheme }
    }
}