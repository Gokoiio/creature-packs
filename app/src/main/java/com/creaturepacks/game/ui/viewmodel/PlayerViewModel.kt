package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.creaturepacks.game.domain.repository.PlayerRepository
import com.creaturepacks.game.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class PlayerViewModel(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val playerStats: Flow<PlayerStatsUi> = combine(
        playerRepository.playerStats(),
        settingsRepository.flowSoundEnabled(),
        settingsRepository.flowMusicEnabled(),
        settingsRepository.flowFragments()
    ) { stats, sound, music, fragments ->
        PlayerStatsUi(
            level = stats.level,
            experience = stats.experience,
            credits = stats.credits,
            packsOpened = stats.packsOpened,
            rareDiscoveries = stats.rareDiscoveries,
            cardsOwned = stats.cardsOwned,
            uniqueCards = stats.uniqueCards,
            totalCardsInSet = stats.totalCardsInSet,
            collectionPercent = stats.collectionPercent(),
            soundEnabled = sound,
            musicEnabled = music,
            fragments = fragments
        )
    }

    data class PlayerStatsUi(
        val level: Int,
        val experience: Int,
        val credits: Int,
        val packsOpened: Int,
        val rareDiscoveries: Int,
        val cardsOwned: Int,
        val uniqueCards: Int,
        val totalCardsInSet: Int,
        val collectionPercent: Float,
        val soundEnabled: Boolean,
        val musicEnabled: Boolean,
        val fragments: Int
    )
}
