package com.strv.movies.ui.auth

sealed class LoginSnackbarNotification(val message: String) {
    object UsernameSnackbar : LoginSnackbarNotification(message = "Please fill in the username")
    object PasswordSnackbar : LoginSnackbarNotification(message = "Please fill in the password")
    object CredentialsError : LoginSnackbarNotification(message = "Wrong credentials")
    object NetworkError : LoginSnackbarNotification(message = "Network error")
    object FunctionNotSupported : LoginSnackbarNotification(message = "Function not supported")
    object GenericError : LoginSnackbarNotification(message = "Something went wrong")
}
