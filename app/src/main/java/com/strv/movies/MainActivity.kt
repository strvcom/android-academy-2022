package com.strv.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.moviedetail.MovieDetail
import com.strv.movies.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(isSystemInDarkTheme) }
            MoviesTheme(useDarkTheme = isDarkTheme.value) {
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
                                Icon(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = rememberRipple(bounded = false),
                                        ) {
                                            isDarkTheme.value = !isDarkTheme.value
                                            changeStatusBarColor(isDarkTheme.value)
                                        },
                                    painter = painterResource(
                                        id = if (isDarkTheme.value) {
                                            R.drawable.ic_light
                                        } else {
                                            R.drawable.ic_dark
                                        }
                                    ),
                                    contentDescription = null,
                                )
                            }
                        )
                        MovieDetail(movie = OfflineMoviesProvider.getMovieDetail(1))
                    }
                }
            }
        }
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
