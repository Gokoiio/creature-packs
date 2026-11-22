package com.creaturepacks.game.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discovered_creatures")
data class CreatureEntity(
    @PrimaryKey val creatureId: String,
    val discoveredAt: Long = System.currentTimeMillis()
)
