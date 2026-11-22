package com.creaturepacks.game.domain.repository

import com.creaturepacks.game.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCards(): Flow<List<Card>>
    fun getCard(cardId: String): Flow<Card?>
    suspend fun addCard(cardId: String, quantity: Int = 1)
    suspend fun setDiscovered(cardId: String)
    suspend fun getCardQuantity(cardId: String): Int
}
