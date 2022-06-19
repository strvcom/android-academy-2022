package com.strv.movies.ui.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.strv.movies.ui.auth.LogInScreen
import com.strv.movies.ui.moviedetail.MovieDetailScreen
import com.strv.movies.ui.movieslist.MoviesListScreen
import com.strv.movies.ui.profile.ProfileScreen

@Composable
fun MoviesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onChangeThemeClick: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MoviesDestinations.MOVIES_LIST_ROUTE
    ) {
        composable(MoviesDestinations.MOVIES_LIST_ROUTE) {
            MoviesListScreen(
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${MoviesDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
                },
                viewModel = hiltViewModel(),
                isDarkTheme = isDarkTheme,
                onChangeThemeClicked = onChangeThemeClick
            )
        }
        composable(
            route = "${MoviesDestinations.MOVIE_DETAIL_ROUTE}/{${MoviesNavArguments.MOVIE_ID_KEY}}",
            arguments = listOf(
                navArgument(MoviesNavArguments.MOVIE_ID_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreen(
                viewModel = hiltViewModel(),

                isDarkTheme = isDarkTheme,
                onChangeThemeClicked = onChangeThemeClick,
                onNavigateBackClick = {
                    navController.navigate(MoviesDestinations.MOVIES_LIST_ROUTE){
                        popUpTo(route = MoviesDestinations.MOVIES_LIST_ROUTE)
                    }
                }
            )
        }
        composable(
            route = MoviesDestinations.PROFILE_ROUTE
        ) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onLogout = {
                    navController.popBackStack(MoviesDestinations.MOVIES_LIST_ROUTE, false)
                }
            )
        }

        composable(
            route = MoviesDestinations.LOGIN_ROUTE
        ) {
            LogInScreen(
                viewModel = hiltViewModel(),
                onSuccessfulLogin = {
                    navController.navigate(MoviesDestinations.PROFILE_ROUTE) {
                        popUpTo(route = MoviesDestinations.LOGIN_ROUTE) {
                            inclusive = true
                        }
                    }
                },
                isDarkTheme = isDarkTheme,
                onChangeThemeClicked = onChangeThemeClick
            )
        }
    }
}