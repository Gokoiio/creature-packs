package com.creaturepacks.game.ui.theme

import androidx.compose.ui.graphics.Color
import com.creaturepacks.game.domain.model.CardRarity

/**
 * Extension helpers to map domain colors to Compose colors at runtime.
 */
fun CardRarity.toComposeColor(): Color = Color(this.color)
fun CardRarity.toComposeDarkColor(): Color = Color(
    when (this) {
        CardRarity.COMMON -> 0xFF475569
        CardRarity.UNCOMMON -> 0xFF064E3B
        CardRarity.RARE -> 0xFF1E3A8A
        CardRarity.EPIC -> 0xFF581C87
        CardRarity.LEGENDARY -> 0xFF713F12
        CardRarity.SECRET -> 0xFF86198F
    }
)

object CreatureColors {
    val Gold = Color(0xFFFBBF24)
    val GoldDark = Color(0xFF713F12)
    val Purple = Color(0xFF8B5CF6)
    val PurpleDark = Color(0xFF5B21B6)
    val BackgroundDark = Color(0xFF0A0B0F)
    val SurfaceDark = Color(0xFF101116)
    val SurfaceElevated = Color(0xFF181921)
    val SurfaceCard = Color(0xFF16171F)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFCBD5E1)
    val TextMuted = Color(0xFF94A3B8)
    val Divider = Color(0xFF272830)
    val ChipBg = Color(0xFF1F2029)
    val Positive = Color(0xFF34D399)
    val Negative = Color(0xFFF87171)
}
