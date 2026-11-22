package com.creaturepacks.game.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.creaturepacks.game.data.local.room.entities.CurrencyTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyTransactionDao {
    @Insert
    suspend fun insert(transaction: CurrencyTransactionEntity)

    @Query("SELECT SUM(amount) FROM currency_transactions")
    fun getBalance(): Flow<Long?>

    @Query("SELECT * FROM currency_transactions ORDER BY timestamp DESC")
    fun getHistory(): Flow<List<CurrencyTransactionEntity>>
}
