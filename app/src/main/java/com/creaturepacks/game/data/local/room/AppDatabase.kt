package com.creaturepacks.game.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.creaturepacks.game.data.local.room.entities.AchievementEntity
import com.creaturepacks.game.data.local.room.entities.CardEntity
import com.creaturepacks.game.data.local.room.entities.CreatureEntity
import com.creaturepacks.game.data.local.room.entities.CurrencyTransactionEntity

@Database(
    entities = [
        CardEntity::class,
        CreatureEntity::class,
        CurrencyTransactionEntity::class,
        AchievementEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun creatureDao(): CreatureDao
    abstract fun currencyTransactionDao(): CurrencyTransactionDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "creature_packs_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
