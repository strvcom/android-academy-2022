package com.strv.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.strv.movies.ui.login.MoviesLoginScreen
import com.strv.movies.ui.moviedetail.MovieDetailScreen
import com.strv.movies.ui.movieslist.MoviesListScreen

@Composable
fun MoviesNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MoviesDestinations.MOVIE_LOGIN_ROUTE
    ) {
        composable(MoviesDestinations.MOVIE_LOGIN_ROUTE) {
            MoviesLoginScreen(
                navigateToMovieList = {
                    navController.navigate(MoviesDestinations.MOVIES_LIST_ROUTE)
                },
                viewModel = hiltViewModel()
            )
        }

        composable(MoviesDestinations.MOVIES_LIST_ROUTE) {
            MoviesListScreen(
                navigateToMovieDetail = { movieId ->
                    navController.navigate("${MoviesDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
                },
                viewModel = hiltViewModel()
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
            MovieDetailScreen(viewModel = hiltViewModel())
        }
    }
}