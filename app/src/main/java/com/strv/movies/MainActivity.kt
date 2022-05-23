package com.strv.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strv.movies.ui.navigation.MoviesNavGraph
import com.strv.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()

            val isDarkTheme by viewModel.isDarkTheme.collectAsState(isSystemInDarkTheme())
            changeStatusBarColor(isDarkTheme)

            MoviesTheme(useDarkTheme = isDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            actions = {
                                DarkLightModeSwitchIcon(
                                    isDarkTheme = isDarkTheme,
                                    changeTheme = viewModel::changeTheme
                                )
                            }
                        )
                        MoviesNavGraph()
                    }
                }
            }
        }
    }

    @Composable
    private fun DarkLightModeSwitchIcon(
        isDarkTheme: Boolean,
        changeTheme: (isDarkTheme: Boolean) -> Unit
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
                    changeTheme(!isDarkTheme)
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

    private fun changeStatusBarColor(isDarkMode: Boolean) {
        val color = if (isDarkMode) R.color.statusBarDarkMode else R.color.statusBarLightMode
        window.statusBarColor = resources.getColor(color, this@MainActivity.theme)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}
