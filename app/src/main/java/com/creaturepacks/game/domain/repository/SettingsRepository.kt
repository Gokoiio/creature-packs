package com.creaturepacks.game.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun flowSoundEnabled(): Flow<Boolean>
    fun flowMusicEnabled(): Flow<Boolean>
    fun flowTutorialCompleted(): Flow<Boolean>
    fun flowFragments(): Flow<Int>
    suspend fun setSoundEnabled(enabled: Boolean)
    suspend fun setMusicEnabled(enabled: Boolean)
    suspend fun setTutorialCompleted(completed: Boolean)
    suspend fun addFragments(amount: Int)
    suspend fun getFragments(): Int
}
