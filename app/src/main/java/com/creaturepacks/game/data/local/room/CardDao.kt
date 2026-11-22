package com.creaturepacks.game.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.creaturepacks.game.data.local.room.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cards: List<CardEntity>)

    @Update
    suspend fun update(card: CardEntity)

    @Query("SELECT * FROM card_inventory ORDER BY cardId")
    fun getAll(): Flow<List<CardEntity>>

    @Query("SELECT quantityOwned FROM card_inventory WHERE cardId = :cardId")
    suspend fun getQuantity(cardId: String): Int

    @Query("DELETE FROM card_inventory")
    suspend fun clearAll()
}
