package com.strv.movies.ui.profile

import android.Manifest
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val state by viewModel.state

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
                    viewModel.ProfileEvent(ProfileEvent.NewAvatar(it))
                },
                removeAvatar = {
                    openDialog = false
                    viewModel.ProfileEvent(ProfileEvent.RemoveAvatar)
                },
                removeEnabled = avatarPath != null
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar(
            url = avatarPath,
            onEditClick = {
                openDialog = true
            }
        )
        Text(
            text = state.user,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 20.sp
        )
        Text(
            text = state.userName,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)

        )
        Button(
            onClick = { viewModel.ProfileEvent(ProfileEvent.LogOut(onLogout)) },
            modifier = Modifier
        ) {
            Text(text = "Logout")
        }
    }
}