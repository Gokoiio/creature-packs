package com.creaturepacks.game.domain.model

/**
 * Rarity of a collectible card. Also drives the weighted drop probabilities used
 * by the (future) booster opening system.
 */
enum class CardRarity(
    val displayName: String,
    val color: Int, // packed ARGB int, resolved by the theme at runtime
    val baseDropRate: Double, // 0..1 probability weight per slot
    val guaranteedEvery: Int? // null = no guarantee; N = at least once per N packs
) {
    COMMON("Common", 0xFF94A3B8, 0.60, null),
    UNCOMMON("Uncommon", 0xFF34D399, 0.25, null),
    RARE("Rare", 0xFF60A5FA, 0.10, null),
    EPIC("Epic", 0xFFC084FC, 0.04, null),
    LEGENDARY("Legendary", 0xFFFBBF24, 0.009, 50),
    SECRET("Secret", 0xFFE879F9, 0.001, 200);

    companion object {
        fun fromName(name: String): CardRarity {
            return values().firstOrNull { it.displayName.equals(name, ignoreCase = true) }
                ?: COMMON
        }
    }
}

/**
 * Original creature type / element. No real-world IP is referenced.
 */
enum class CreatureType(val displayName: String, val color: Int) {
    EMBER("Ember", 0xFFF87171),
    FROST("Frost", 0xFF93C5FD),
    LEAF("Leaf", 0xFF86EFAC),
    SPARK("Spark", 0xFFFCD34D),
    STONE("Stone", 0xFFE5E7EB),
    SHADOW("Shadow", 0xFFC4B5FD),
    LIGHT("Light", 0xFFFEF08A),
    CRYSTAL("Crystal", 0xFFEDE9FE);

    companion object {
        fun fromName(name: String): CreatureType {
            return values().firstOrNull { it.displayName.equals(name, ignoreCase = true) }
                ?: EMBER
        }
    }
}

/**
 * A collectible card (domain model, persistence-independent).
 */
data class Card(
    val id: String,
    val name: String,
    val creatureName: String,
    val type: CreatureType,
    val rarity: CardRarity,
    val illustration: String, // drawable resource name (temporary artwork placeholder)
    val description: String,
    val setCode: String,
    val collectorNumber: Int,
    val isDiscovered: Boolean = false,
    val quantityOwned: Int = 0
) {
    fun isOwned(): Boolean = quantityOwned > 0
    fun isMissing(): Boolean = quantityOwned == 0
}

/**
 * A booster series available in the shop.
 */
data class BoosterSeries(
    val code: String,
    val title: String,
    val description: String,
    val price: Int,
    val illustration: String,
    val isUnlocked: Boolean = true,
    val unlockRequirementSeries: String? = null
)

/**
 * Player-level statistics surfaced on Home and Profile.
 */
data class PlayerStats(
    var level: Int = 1,
    var experience: Int = 0,
    var credits: Int = 500, // starting virtual currency
    var packsOpened: Int = 0,
    var rareDiscoveries: Int = 0,
    var cardsOwned: Int = 0,
    var uniqueCards: Int = 0,
    var totalCardsInSet: Int = 0
) {
    fun collectionPercent(): Float = if (totalCardsInSet == 0) 0f else (uniqueCards.toFloat() / totalCardsInSet) * 100f
}

/**
 * An achievement definition.
 */
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val targetValue: Int,
    var currentValue: Int = 0,
    var unlocked: Boolean = false
) {
    fun isComplete(): Boolean = currentValue >= targetValue
}
