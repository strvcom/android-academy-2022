package com.strv.movies.ui.profile

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.strv.movies.ui.profile.components.AvatarEditDialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.ui.profile.components.AvatarPermissionsDialog
import com.strv.movies.ui.profile.components.ProfileAvatar
import com.strv.movies.utils.extentions.openSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val avatarPath by viewModel.avatarPath.collectAsState(initial = null)
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (openDialog) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                AvatarPermissionsDialog(
                    positiveText = "GRANT PERMISSIONS",
                    negative = {
                        openDialog = false
                    },
                    positive = {
                        permissionState.launchPermissionRequest()
                    }
                )
            },
            permissionNotAvailableContent = {
                AvatarPermissionsDialog(
                    positiveText = "OPEN SETTINGS",
                    negative = {
                        openDialog = false
                    },
                    positive = context::openSettings
                )
            }) {
            AvatarEditDialog(
                onDismiss = { openDialog = false },
                editAvatar = {
                    openDialog = false
                    viewModel.onNewAvatar(it)
                },
                removeAvatar = {
                    openDialog = false
                    viewModel.removeAvatar()
                },
                removeEnabled = avatarPath != null
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar(
            url = avatarPath,
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