package com.creaturepacks.game.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.creaturepacks.game.data.local.room.entities.CreatureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(creature: CreatureEntity)

    @Query("SELECT * FROM discovered_creatures")
    fun getAllDiscovered(): Flow<List<CreatureEntity>>

    @Query("SELECT COUNT(*) FROM discovered_creatures WHERE creatureId = :creatureId")
    suspend fun isDiscovered(creatureId: String): Boolean
}
