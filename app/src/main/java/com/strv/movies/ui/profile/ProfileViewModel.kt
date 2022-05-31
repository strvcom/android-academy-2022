package com.strv.movies.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.extension.fold
import com.strv.movies.network.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun logout(
        onSuccess: () -> Unit
    ){
        viewModelScope.launch {
            authRepository.logOut().fold(
                {
                    Log.d("TAG", "Logout failed")
                },
                {
                    onSuccess()
                }
            )
        }
    }
}