package com.creaturepacks.game.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesDataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val FILE_NAME = "creature_packs_settings.pb"

object SettingsDataStore {
    private var dataStore: DataStore<Preferences>? = null

    fun init(context: Context) {
        if (dataStore == null) {
            dataStore = preferencesDataStore(context = context, fileName = FILE_NAME)
        }
    }

    private val store: DataStore<Preferences>
        get() = dataStore ?: throw IllegalStateException(
            "SettingsDataStore not initialized. Call SettingsDataStore.init(context) first."
        )

    // Keys
    private val SOUND_ENABLED = stringPreferencesKey("sound_enabled")
    private val MUSIC_ENABLED = stringPreferencesKey("music_enabled")
    private val TUTORIAL_COMPLETED = stringPreferencesKey("tutorial_completed")
    private val FRAGMENTS = intPreferencesKey("fragments")

    suspend fun setSoundEnabled(enabled: Boolean) {
        store.edit { preferences -> preferences[SOUND_ENABLED] = enabled.toString() }
    }

    suspend fun setMusicEnabled(enabled: Boolean) {
        store.edit { preferences -> preferences[MUSIC_ENABLED] = enabled.toString() }
    }

    suspend fun setTutorialCompleted(completed: Boolean) {
        store.edit { preferences -> preferences[TUTORIAL_COMPLETED] = completed.toString() }
    }

    suspend fun setFragments(amount: Int) {
        store.edit { preferences -> preferences[FRAGMENTS] = amount }
    }

    suspend fun addFragments(amount: Int) {
        val current = store.data.first()[FRAGMENTS] ?: 0
        store.edit { preferences -> preferences[FRAGMENTS] = current + amount }
    }

    fun flowSoundEnabled(): Flow<Boolean> =
        store.data.map { it[SOUND_ENABLED]?.toBoolean() ?: true }

    fun flowMusicEnabled(): Flow<Boolean> =
        store.data.map { it[MUSIC_ENABLED]?.toBoolean() ?: true }

    fun flowTutorialCompleted(): Flow<Boolean> =
        store.data.map { it[TUTORIAL_COMPLETED]?.toBoolean() ?: false }

    fun flowFragments(): Flow<Int> =
        store.data.map { it[FRAGMENTS] ?: 0 }

    suspend fun isSoundEnabled(): Boolean =
        store.data.first().let { prefs -> prefs[SOUND_ENABLED]?.toBoolean() ?: true }

    suspend fun isMusicEnabled(): Boolean =
        store.data.first().let { prefs -> prefs[MUSIC_ENABLED]?.toBoolean() ?: true }

    suspend fun isTutorialCompleted(): Boolean =
        store.data.first().let { prefs -> prefs[TUTORIAL_COMPLETED]?.toBoolean() ?: false }

    suspend fun getFragments(): Int =
        store.data.first().let { prefs -> prefs[FRAGMENTS] ?: 0 }
}
