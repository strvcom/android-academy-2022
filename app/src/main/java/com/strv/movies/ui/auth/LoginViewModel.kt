package com.strv.movies.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
}