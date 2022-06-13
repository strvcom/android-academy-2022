package com.strv.movies.ui.auth

data class LoginViewState(
    val user: String = "",
    val password: String = "",
    val error: String? = null
)