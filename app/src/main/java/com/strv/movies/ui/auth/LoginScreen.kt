package com.strv.movies.ui.auth

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onSuccessfulLogin: () -> Unit,
) {

    Button(onClick = {
        viewModel.login(onSuccessfulLogin)
    }) {
        Text(text = "Login")
    }
}