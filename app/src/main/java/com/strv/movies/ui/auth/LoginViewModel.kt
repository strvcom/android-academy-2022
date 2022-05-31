package com.strv.movies.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.database.AuthDataStore
import com.strv.movies.extension.fold
import com.strv.movies.network.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val  error = _error.asStateFlow()
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logIn(
                username = "jipariz",
                password = "nYjfym-firqum-wonca8"
            ).fold(
                { error ->
                    _error.update {
                        error.toString()
                    }
                },
                {
                    onSuccess()
                }
            )
        }

    }
}