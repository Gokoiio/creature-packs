package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creaturepacks.game.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val soundEnabled: Flow<Boolean> = settingsRepository.flowSoundEnabled()
    val musicEnabled: Flow<Boolean> = settingsRepository.flowMusicEnabled()
    val tutorialCompleted: Flow<Boolean> = settingsRepository.flowTutorialCompleted()

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setSoundEnabled(enabled)
        }
    }

    fun setMusicEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setMusicEnabled(enabled)
        }
    }

    fun setTutorialCompleted(completed: Boolean) {
        viewModelScope.launch {
            settingsRepository.setTutorialCompleted(completed)
        }
    }
}
