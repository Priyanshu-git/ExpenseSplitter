package com.nexxlabs.expensesplitter.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nexxlabs.expensesplitter.ui.screens.settings.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class ThemePreferences @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private val THEME_KEY = stringPreferencesKey("theme_mode")

    val themeMode: Flow<ThemeMode> =
        context.dataStore.data.map { prefs ->
            ThemeMode.valueOf(
                prefs[THEME_KEY] ?: ThemeMode.SYSTEM.name
            )
        }

    suspend fun setTheme(mode: ThemeMode) {
        context.dataStore.edit {
            it[THEME_KEY] = mode.name
        }
    }
}
