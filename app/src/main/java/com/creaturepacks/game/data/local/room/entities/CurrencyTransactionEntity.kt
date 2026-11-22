package com.creaturepacks.game.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_transactions")
data class CurrencyTransactionEntity(
    @PrimaryKey val id: String,
    val amount: Int, // positive = earned, negative = spent
    val timestamp: Long = System.currentTimeMillis(),
    val description: String
)
