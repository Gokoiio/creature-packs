package com.creaturepacks.game.data.repository

import com.creaturepacks.game.data.local.room.AppDatabase
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CollectionRepositoryImpl(
    private val cardRepository: CardRepository,
    private val db: AppDatabase
) : CollectionRepository {

    override fun getOwnedCards(): Flow<List<Card>> =
        cardRepository.getAllCards().map { it.filter { it.quantityOwned > 0 } }

    override fun getMissingCards(): Flow<List<Card>> =
        cardRepository.getAllCards().map { it.filter { it.quantityOwned == 0 } }

    override fun collectionProgress(): Flow<Float> =
        cardRepository.getAllCards().map { cards ->
            if (cards.isEmpty()) 0f
            else cards.count { it.quantityOwned > 0 }.toFloat() / cards.size * 100f
        }

    override suspend fun markCreatureDiscovered(creatureId: String) {
        db.creatureDao().insert(
            com.creaturepacks.game.data.local.room.entities.CreatureEntity(creatureId = creatureId)
        )
    }
}
