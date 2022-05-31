package com.strv.movies.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    onLogOut: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    Column {
        Text(text = "Profile screen", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = {
            viewModel.logOut()
            onLogOut()
        }) {
            Text(text = "Logout")
        }
    }
}