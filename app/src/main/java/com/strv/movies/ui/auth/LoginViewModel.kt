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
                username = "YOUR NAME HERE",
                password = "YOUR PASSWORD HERE"
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
        Log.d("TAG", "LoginViewModel - ${_viewState.value.user}")
    }

    fun updatePassword(input: String) {
        _viewState.update {
            it.copy(password = input)

        }
        Log.d("TAG", "LoginViewModel - ${_viewState.value.password}")
    }

    fun login(): Boolean {
        val userName = _viewState.value.user
        val password = _viewState.value.password
        if (userName.isNotBlank() && password.isNotBlank()) {
            return true
        } else {
            _viewState.update { it.copy(error = "Please fill in user name and password") }
            Log.d("TAG", "LoginViewModel - error")
            return false
        }
    }

}