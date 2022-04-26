package com.strv.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.movieslist.MoviesList

@Composable
fun MoviesNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MoviesDestinations.MOVIES_LIST_ROUTE
    ) {
        composable(MoviesDestinations.MOVIES_LIST_ROUTE) {
            MoviesList(movies = OfflineMoviesProvider.getMovies())
        }
    }
}