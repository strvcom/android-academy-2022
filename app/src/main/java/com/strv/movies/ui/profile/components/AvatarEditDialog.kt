package com.strv.movies.ui.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AvatarEditDialog(
    onDismiss: () -> Unit,
    editAvatar: (String) -> Unit,
    removeAvatar: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),

            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // TODO
                    }) {
                    Text(text = "CAMERA")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // TODO
                    }) {
                    Text(text = "LIBRARY")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = removeAvatar,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error,
                        contentColor = MaterialTheme.colors.onError
                    )
                ) {
                    Text(text = "DELETE")
                }
            }
        }
    }
}

@Preview
@Composable
private fun AvatarEditDialogPreview() {
    AvatarEditDialog(
        onDismiss = { },
        editAvatar = { },
        removeAvatar = {}
    )
}