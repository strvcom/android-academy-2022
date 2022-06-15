package com.strv.movies.ui.profile

sealed class ProfileEvent {
    data class LogOut(val onSuccess: () -> Unit) : ProfileEvent()
    object RemoveAvatar: ProfileEvent()
    data class NewAvatar(val path: String): ProfileEvent()
}
