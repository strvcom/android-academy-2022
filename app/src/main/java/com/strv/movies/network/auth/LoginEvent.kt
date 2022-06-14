package com.strv.movies.network.auth

sealed class LoginEvent(){
    data class UpdateUsername(val input: String): LoginEvent()
    data class UpdatePassword(val input: String): LoginEvent()
    data class Login(val onSuccess: () -> Unit): LoginEvent()
}
