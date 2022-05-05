package com.strv.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.movieslist.MoviesList
import com.strv.movies.ui.moviedetail.MovieDetailScreen

@Composable
fun MoviesNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MoviesDestinations.MOVIES_LIST_ROUTE
    ) {
        composable(MoviesDestinations.MOVIES_LIST_ROUTE) {
            MoviesList(
                movies = OfflineMoviesProvider.getMovies(),
                onMovieClick = { movieId ->
                    navController.navigate("${MoviesDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
                }
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