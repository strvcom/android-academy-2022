package com.strv.movies.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.strv.movies.database.AuthDataStore

@Composable
fun MoviesBottomNavigation(
    navController: NavHostController = rememberNavController(),
    authDataStore: AuthDataStore
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isAuthenticated by authDataStore.isAuthenticatedFlow.collectAsState(initial = false)
    BottomNavigation {
        listOf(
            BottomNavigationDestinations.MovieList,
            BottomNavigationDestinations.UserProfile
        ).forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.navIcon, contentDescription = null) },
                label = { Text(stringResource(screen.navTitleResId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    // Uncomment this to require user to be logged to open profile screen
                    if (screen is BottomNavigationDestinations.UserProfile && isAuthenticated.not()) {
                        navController.navigate(MoviesDestinations.LOGIN_ROUTE)
                        return@BottomNavigationItem
                    }
                    navController.navigate(screen.route) {

                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().route!!) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}