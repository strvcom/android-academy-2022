package com.strv.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.strv.movies.ui.navigation.MoviesBottomNavigation
import com.strv.movies.ui.navigation.MoviesDestinations
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
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            var bottomBarVisibility by rememberSaveable { (mutableStateOf(false)) }
            // Show bottomNavigation only for MoviesDestinations.MOVIES_LIST_ROUTE and MoviesDestinations.PROFILE_ROUTE
            bottomBarVisibility =
                when (navBackStackEntry?.destination?.route?.substringBefore('/')) {
                    MoviesDestinations.MOVIE_DETAIL_ROUTE, MoviesDestinations.LOGIN_ROUTE -> false
                    else -> true
                }
            changeStatusBarColor(isDarkTheme)

            MoviesTheme(useDarkTheme = isDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = bottomBarVisibility,
                                enter = slideInVertically { it },
                                exit = slideOutVertically { it },
                            ) {
                                MoviesBottomNavigation(
                                    navController = navController,
                                    authDataStore = viewModel.authDataStore
                                )
                            }
                        }
                    ) { paddingValues ->
                        MoviesNavGraph(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            isDarkTheme = isDarkTheme,
                            onChangeThemeClick = {
                                viewModel.changeTheme(!isDarkTheme)
                            }
                        )
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
