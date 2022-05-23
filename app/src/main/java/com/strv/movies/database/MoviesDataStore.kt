package com.strv.movies.database

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val MOVIES_APP_DATASTORE = "movies_app_datastore"

private val DARK_THEME_ENABLED = booleanPreferencesKey("dark_theme_enabled")

@Singleton
class MoviesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(MOVIES_APP_DATASTORE)
    }

    val darkModeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_ENABLED] ?: false
        }

    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_ENABLED] = isDarkTheme
        }
    }
}
