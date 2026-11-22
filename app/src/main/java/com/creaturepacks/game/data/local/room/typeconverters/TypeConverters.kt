package com.creaturepacks.game.data.local.room.typeconverters

import androidx.room.TypeConverter
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.domain.model.CreatureType

object TypeConverters {

    @TypeConverter
    @JvmStatic
    fun rarityToString(rarity: CardRarity?): String? = rarity?.name

    @TypeConverter
    @JvmStatic
    fun stringToRarity(name: String?): CardRarity? = name?.let { CardRarity.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun typeToString(type: CreatureType?): String? = type?.name

    @TypeConverter
    @JvmStatic
    fun stringToType(name: String?): CreatureType? = name?.let { CreatureType.valueOf(it) }
}
