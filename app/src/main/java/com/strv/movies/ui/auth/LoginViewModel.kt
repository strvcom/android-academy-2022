package com.strv.movies.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logIn(
                username = _viewState.value.user,
                password = _viewState.value.password
            ).fold(
                { error ->
                    Log.d("TAG", "Login failed - ${error.name}")
                },
                {
                    onSuccess()
                }
            )
        }
    }

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()

    fun updateName(input: String) {
        _viewState.update {
            it.copy(user = input)
        }
    }

    fun updatePassword(input: String) {
        _viewState.update {
            it.copy(password = input)

        }
    }
}