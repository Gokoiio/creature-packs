package com.creaturepacks.game.data.repository

import android.content.Context
import com.creaturepacks.game.data.local.room.AppDatabase
import com.creaturepacks.game.data.local.datastore.SettingsDataStore
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.domain.model.CreatureType
import com.creaturepacks.game.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardRepositoryImpl(
    private val db: AppDatabase,
    private val context: Context
) : CardRepository {

    override fun getAllCards(): Flow<List<Card>> =
        db.cardDao().getAll().map { entities ->
            sampleCards(context).map { sample ->
                val entity = entities.firstOrNull { it.cardId == sample.id }
                sample.copy(
                    quantityOwned = entity?.quantityOwned ?: 0
                )
            }
        }

    override fun getCard(cardId: String): Flow<Card?> =
        getAllCards().map { it.firstOrNull { it.id == cardId } }

    override suspend fun addCard(cardId: String, quantity: Int = 1) {
        val current = db.cardDao().getQuantity(cardId)
        db.cardDao().upsert(
            CardEntity(cardId = cardId, quantityOwned = current + quantity)
        )
        // Mark the creature discovered when the card is obtained.
        db.creatureDao().insert(com.creaturepacks.game.data.local.room.entities.CreatureEntity(creatureId = cardId))
    }

    override suspend fun setDiscovered(cardId: String) {
        db.creatureDao().insert(
            com.creaturepacks.game.data.local.room.entities.CreatureEntity(creatureId = cardId)
        )
    }

    override suspend fun getCardQuantity(cardId: String): Int {
        return db.cardDao().getQuantity(cardId)
    }
}
