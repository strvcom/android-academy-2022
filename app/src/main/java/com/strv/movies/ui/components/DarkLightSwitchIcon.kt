package com.strv.movies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strv.movies.R

@Composable
 fun DarkLightModeSwitchIcon(
    isDarkTheme: Boolean,
    changeTheme: () -> Unit
) {
    Icon(
        modifier = Modifier
            .padding(end = 12.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(bounded = false),
            ) {
                changeTheme()
            },
        painter = painterResource(
            id = if (isDarkTheme) {
                R.drawable.ic_light
            } else {
                R.drawable.ic_dark
            }
        ),
        contentDescription = null,
    )
}

