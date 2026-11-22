package com.creaturepacks.game.data.repository

import com.creaturepacks.game.data.local.room.AppDatabase
import com.creaturepacks.game.data.local.datastore.SettingsDataStore
import com.creaturepacks.game.domain.model.PlayerStats
import com.creaturepacks.game.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val db: AppDatabase,
    private val settingsDataStore: SettingsDataStore
) : PlayerRepository {

    override fun playerStats(): Flow<PlayerStats> =
        db.currencyTransactionDao().getBalance().map { balance ->
            PlayerStats(
                credits = (balance ?: 0L).toInt()
            )
        }

    override suspend fun addCredits(amount: Int) {
        val id = "tx_${System.currentTimeMillis()}_$amount"
        db.currencyTransactionDao().insert(
            com.creaturepacks.game.data.local.room.entities.CurrencyTransactionEntity(
                id = id,
                amount = amount,
                description = "Credits earned"
            )
        )
    }

    override suspend fun spendCredits(amount: Int): Boolean {
        // Read current balance synchronously via the first balance emission.
        val balance = db.currencyTransactionDao().getBalance().first()
        val current = (balance ?: 0L).toInt()
        if (current < amount) return false
        val id = "tx_${System.currentTimeMillis()}_neg$amount"
        db.currencyTransactionDao().insert(
            com.creaturepacks.game.data.local.room.entities.CurrencyTransactionEntity(
                id = id,
                amount = -amount,
                description = "Credits spent"
            )
        )
        return true
    }

    override suspend fun incrementPacksOpened() {
        // No dedicated counter table in milestone 1; tracked via transaction history.
    }

    override suspend fun incrementRareDiscoveries() {
        // No dedicated counter table in milestone 1.
    }

    override suspend fun addFragments(amount: Int) {
        settingsDataStore.addFragments(amount)
    }

    override fun fragments(): Flow<Int> = settingsDataStore.flowFragments()
}
