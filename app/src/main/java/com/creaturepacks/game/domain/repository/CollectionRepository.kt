package com.creaturepacks.game.domain.repository

import com.creaturepacks.game.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getOwnedCards(): Flow<List<Card>>
    fun getMissingCards(): Flow<List<Card>>
    fun collectionProgress(): Flow<Float>
    suspend fun markCreatureDiscovered(creatureId: String)
}
