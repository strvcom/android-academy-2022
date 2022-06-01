package com.strv.movies.ui.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileAvatar(
    url: String?,
    onEditClick: () -> Unit
){
    Box {
        AsyncImage(
            model = url,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary),
            fallback = rememberVectorPainter(
                image = Icons.Default.Person
            ),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(PaddingValues())
                .size(40.dp)
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    brush = SolidColor(MaterialTheme.colors.surface)
                )
                .padding(2.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.primary)
                .align(Alignment.BottomEnd)
                .clickable(onClick = onEditClick)
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Default.Edit),
                tint = MaterialTheme.colors.surface,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
private fun ProfileAvatarPreview(){
    ProfileAvatar(url = "https://www.looper.com/img/gallery/20-epic-movies-like-avatar-you-need-to-watch-next/l-intro-1645555067.jpg") {}
}