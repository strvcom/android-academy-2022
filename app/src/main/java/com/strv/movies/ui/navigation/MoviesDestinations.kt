package com.strv.movies.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.strv.movies.R
import com.strv.movies.ui.navigation.MoviesDestinations.MOVIES_LIST_ROUTE
import com.strv.movies.ui.navigation.MoviesDestinations.PROFILE_ROUTE

object MoviesDestinations {
    const val MOVIES_LIST_ROUTE = "movies_list"
    const val MOVIE_DETAIL_ROUTE = "movie_detail"
    const val PROFILE_ROUTE = "user_profile"
    const val LOGIN_ROUTE = "login"
}

sealed class BottomNavigationDestinations(
    val route: String,
    @StringRes val navTitleResId: Int,
    val navIcon: ImageVector
) {
    object MovieList : BottomNavigationDestinations(
        route = MOVIES_LIST_ROUTE,
        navTitleResId = R.string.movie_list_title,
        navIcon = Icons.Default.List
    )

    object UserProfile : BottomNavigationDestinations(
        route = PROFILE_ROUTE,
        navTitleResId = R.string.profile_title,
        navIcon = Icons.Default.Person
    )
}


