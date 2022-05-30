package com.strv.movies.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strv.movies.ui.profile.components.AvatarEditDialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.ui.profile.components.ProfileAvatar

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var openDialog by remember { mutableStateOf(false) }

        if(openDialog){
            AvatarEditDialog(
                onDismiss = { openDialog = false },
                editAvatar = {},
                removeAvatar = {}
            )
        }

        ProfileAvatar(
            url = "https://www.looper.com/img/gallery/20-epic-movies-like-avatar-you-need-to-watch-next/l-intro-1645555067.jpg",
            onEditClick = {
                openDialog = true
            }
        )
        Button(
            onClick = { viewModel.logout(onSuccess = onLogout) }
        ) {
            Text(text = "Logout")
        }
    }
}