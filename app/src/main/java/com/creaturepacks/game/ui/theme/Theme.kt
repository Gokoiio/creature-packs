package com.creaturepacks.game.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8B5CF6),
    primaryContainer = Color(0xFF5B21B6),
    secondary = Color(0xFFFBBF24),
    secondaryContainer = Color(0xFF713F12),
    tertiary = Color(0xFF9F7AEA),
    background = Color(0xFF0A0B0F),
    surface = Color(0xFF101116),
    surfaceVariant = Color(0xFF181921),
    outline = Color(0xFF272830),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF1E1B16),
    onTertiary = Color(0xFFFFFFFF),
    inverseSurface = Color(0xFFE2E8F0),
    inversePrimary = Color(0xFF5B21B6),
    error = Color(0xFFF87171),
    onError = Color(0xFF1E1B16)
)

@Composable
fun CreaturePacksTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
