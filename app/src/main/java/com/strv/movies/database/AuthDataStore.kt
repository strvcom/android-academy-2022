package com.strv.movies.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private const val SESSION_TOKEN = "accessToken"
private const val AUTH_DATASTORE = "auth_datastore"


@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object{
        val SESSION_TOKEN_KEY = stringPreferencesKey(SESSION_TOKEN)
    }

    private val dataStore = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(AUTH_DATASTORE)
    }

    val sessionToken: String? get() = dataStore.getOrNull(SESSION_TOKEN_KEY)

    val isAuthenticated: Boolean get() = sessionToken != null
    val isAuthenticatedFlow: Flow<Boolean> get() = dataStore.getOrNullFlow(SESSION_TOKEN_KEY).map { it != null }

    suspend fun updateSessionToken(token: String) {
        dataStore.updateValue(SESSION_TOKEN_KEY, token)
    }

    suspend fun deleteSessionToken(){
        dataStore.edit { it.remove(SESSION_TOKEN_KEY) }
    }

}

fun <T> DataStore<Preferences>.getOrNull(key: Preferences.Key<T>): T? {
    return runBlocking { getOrNullFlow(key).first() }
}

fun <T> DataStore<Preferences>.getOrNullFlow(key: Preferences.Key<T>): Flow<T?> {
    return data.map { preferences ->
        preferences[key]
    }
}

suspend inline fun <T> DataStore<Preferences>.updateValue(key: Preferences.Key<T>, value: T?) {
    edit {
        it.update(key, value)
    }
}

fun <T> MutablePreferences.update(key: Preferences.Key<T>, value: T?) {
    value?.let { this[key] = it } ?: remove(key)
}
