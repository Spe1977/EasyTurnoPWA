package com.easyturno.ui.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Header: Gestore per il tema dell'applicazione.
 *
 * Questa classe gestisce la persistenza della preferenza del tema dell'utente
 * (Chiaro, Scuro, Dinamico) utilizzando Jetpack DataStore.
 */

// Definisce l'enum per le modalità di tema supportate.
enum class ThemeMode {
    LIGHT,
    DARK,
    DYNAMIC
}

// Estensione per creare una singola istanza di DataStore per tutta l'app.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemeManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    // La chiave per salvare la preferenza del tema in DataStore.
    private val themeKey = stringPreferencesKey("theme_mode")

    /**
     * Un Flow che emette la modalità di tema corrente ogni volta che cambia.
     * Il valore di default è DYNAMIC.
     */
    val themeMode: Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            // Legge la stringa dal datastore e la converte nell'enum.
            // Se non trova nulla o il valore non è valido, usa DYNAMIC.
            ThemeMode.valueOf(preferences[themeKey] ?: ThemeMode.DYNAMIC.name)
        }

    /**
     * Salva la preferenza del tema dell'utente.
     */
    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { settings ->
            settings[themeKey] = themeMode.name
        }
    }
}
