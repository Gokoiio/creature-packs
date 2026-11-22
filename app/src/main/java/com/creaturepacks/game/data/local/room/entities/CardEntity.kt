package com.creaturepacks.game.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_inventory")
data class CardEntity(
    @PrimaryKey val cardId: String,
    val quantityOwned: Int = 0,
    val discoveredAt: Long = System.currentTimeMillis()
)
