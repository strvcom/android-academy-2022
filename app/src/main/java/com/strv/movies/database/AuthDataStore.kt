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

private val SESSION_TOKEN = stringPreferencesKey("accessToken")
private const val AUTH_DATASTORE = "auth_datastore"


@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(AUTH_DATASTORE)
    }

    val sessionToken: String? get() = runBlocking {
        dataStore.data
            .map { preferences ->
                preferences[SESSION_TOKEN]
            }.first()
    }
    val isAuthenticated: Boolean get() = sessionToken != null
    val isAuthenticatedFlow: Flow<Boolean> get() =  dataStore.data
        .map { preferences ->
            preferences[SESSION_TOKEN] != null
        }

    suspend fun updateSessionToken(token: String) {
        dataStore.edit { it[SESSION_TOKEN] = token }
    }

    suspend fun deleteSessionToken(){
        dataStore.edit { it.remove(SESSION_TOKEN) }
    }
}

