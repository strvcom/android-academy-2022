package com.strv.movies.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.strv.movies.R

@Composable
fun CustomTopAppBar(
    isDarkTheme: Boolean,
    onChangeThemeClick: () -> Unit,
    showNavIcon: Boolean = false,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            DarkLightModeSwitchIcon(
                isDarkTheme = isDarkTheme,
                changeTheme = onChangeThemeClick
            )
        },
        navigationIcon = {
            if (showNavIcon) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(
                        R.string.detailScreen_navigateBack
                    ),
                    modifier = modifier
                )
            }
        }
    )
}
