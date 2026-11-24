package com.creaturepacks.game.ui.util

import com.creaturepacks.game.data.sample.BoosterSeries
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.R

fun getPackDrawable(name: String): Int = when (name) {
    "Genesis" -> R.drawable.pack_genesis
    "Ember" -> R.drawable.pack_ember
    "Frost" -> R.drawable.pack_frost
    "Crystal" -> R.drawable.pack_crystal
    else -> R.drawable.pack_genesis
}

fun getArtDrawableForCard(card: Card, isOwned: Boolean): Int = when (card.rarity) {
    CardRarity.COMMON -> if (isOwned) R.drawable.art_common else R.drawable.art_template_common
    CardRarity.UNCOMMON -> if (isOwned) R.drawable.art_uncommon else R.drawable.art_template_uncommon
    CardRarity.RARE -> if (isOwned) R.drawable.art_rare else R.drawable.art_template_rare
    CardRarity.EPIC -> if (isOwned) R.drawable.art_epic else R.drawable.art_template_epic
    CardRarity.LEGENDARY -> if (isOwned) R.drawable.art_legendary else R.drawable.art_template_legendary
    CardRarity.SECRET -> if (isOwned) R.drawable.art_secret else R.drawable.art_template_secret
}
