package com.creaturepacks.game.domain.repository

import com.creaturepacks.game.domain.model.PlayerStats
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun playerStats(): Flow<PlayerStats>
    suspend fun addCredits(amount: Int)
    suspend fun spendCredits(amount: Int): Boolean
    suspend fun incrementPacksOpened()
    suspend fun incrementRareDiscoveries()
    suspend fun addFragments(amount: Int)
    fun fragments(): Flow<Int>
}
