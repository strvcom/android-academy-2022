package com.strv.movies.ui.auth

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onSuccessfulLogin: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val error by viewModel.error.collectAsState(initial = null)
    Button(onClick = {
        viewModel.login(onSuccessfulLogin)
    }) {
        Text(text = "Login")
    }

    Text(text = error ?: "")
}