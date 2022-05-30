package com.strv.movies.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strv.movies.database.MoviesDataStore
import com.strv.movies.extension.fold
import com.strv.movies.network.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val moviesDataStore: MoviesDataStore
) : ViewModel() {
    val avatarPath = moviesDataStore.avatarPathFlow

    fun onNewAvatar(newAvatarPath: String) {
        viewModelScope.launch {
            moviesDataStore.avatarPathFlow.first()?.let {
                File(it).delete()
            }
            moviesDataStore.setAvatarPath(newAvatarPath)
        }
    }

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