package com.creaturepacks.game.data.repository

import com.creaturepacks.game.data.local.datastore.SettingsDataStore
import com.creaturepacks.game.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val settings: SettingsDataStore) : SettingsRepository {
    override fun flowSoundEnabled(): Flow<Boolean> = settings.flowSoundEnabled()
    override fun flowMusicEnabled(): Flow<Boolean> = settings.flowMusicEnabled()
    override fun flowTutorialCompleted(): Flow<Boolean> = settings.flowTutorialCompleted()
    override fun flowFragments(): Flow<Int> = settings.flowFragments()
    override suspend fun setSoundEnabled(enabled: Boolean) = settings.setSoundEnabled(enabled)
    override suspend fun setMusicEnabled(enabled: Boolean) = settings.setMusicEnabled(enabled)
    override suspend fun setTutorialCompleted(completed: Boolean) = settings.setTutorialCompleted(completed)
    override suspend fun addFragments(amount: Int) = settings.addFragments(amount)
    override suspend fun getFragments(): Int = settings.getFragments()
}
