package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creaturepacks.game.data.sample.BOOSTER_SERIES
import com.creaturepacks.game.domain.repository.PlayerRepository
import com.creaturepacks.game.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val featuredBoosters = BOOSTER_SERIES.filter { it.isUnlocked }.take(3)

    val playerStats: Flow<PlayerStatsUi> = combine(
        playerRepository.playerStats(),
        settingsRepository.flowTutorialCompleted(),
        settingsRepository.flowFragments()
    ) { stats, tutorialDone, fragments ->
        PlayerStatsUi(
            level = stats.level,
            experience = stats.experience,
            credits = stats.credits,
            packsOpened = stats.packsOpened,
            rareDiscoveries = stats.rareDiscoveries,
            collectionPercent = stats.collectionPercent(),
            tutorialCompleted = tutorialDone,
            fragments = fragments
        )
    }

    fun claimDailyReward() {
        viewModelScope.launch {
            playerRepository.addCredits(50)
        }
    }

    data class PlayerStatsUi(
        val level: Int,
        val experience: Int,
        val credits: Int,
        val packsOpened: Int,
        val rareDiscoveries: Int,
        val collectionPercent: Float,
        val tutorialCompleted: Boolean,
        val fragments: Int
    )
}
