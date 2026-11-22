package com.creaturepacks.game.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val currentValue: Int = 0,
    val unlocked: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
