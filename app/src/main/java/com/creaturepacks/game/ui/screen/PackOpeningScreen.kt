package com.creaturepacks.game.ui.screen

import androidx.compose.animation.animateFloatAsState
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creaturepacks.game.data.sample.BoosterSeries
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.ui.component.CreatureColors
import com.creaturepacks.game.ui.component.CurrencyDisplay
import com.creaturepacks.game.ui.component.RarityBadge
import com.creaturepacks.game.ui.component.SectionHeader
import com.creaturepacks.game.ui.theme.CreaturePacksTheme
import com.creaturepacks.game.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PackOpeningScreen(
    series: BoosterSeries,
    onCardsRevealed: (List<Card>) -> Unit,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawnCards = remember { mutableStateListOf<Card>() }
    val currentRevealIndex = remember { mutableStateOf(0) }
    val isOpening = remember { mutableStateOf(true) }
    val showResults = remember { mutableStateOf(false) }
    val revealAllTriggered = remember { mutableStateOf(false) }
    val packRotation by animateFloatAsState(0f)
    val packScale by animateFloatAsState(1f)
    val packGlow by animateFloatAsState(0f)

    // Simulate drawing 5 cards from the series
    LaunchedEffect(Unit) {
        if (isOpening.value) {
            delay(800) // Wait for pack to appear
            openPack()
        }
    }

    fun openPack() {
        scope.launch {
            // Pack opening animation
            packScale.value = 1.2f
            packRotation.value = 5f
            delay(100)
            packScale.value = 1.0f
            packRotation.value = -5f
            delay(100)
            packScale.value = 1.1f
            packRotation.value = 0f
            delay(200)
            
            // Burst effect
            packGlow.value = 1f
            delay(300)
            packGlow.value = 0f
            
            isOpening.value = false
            startRevealingCards()
        }
    }

    fun startRevealingCards() {
        scope.launch {
            val cards = generatePackContents(series)
            for (i in cards.indices) {
                if (revealAllTriggered.value) {
                    // Reveal all remaining at once
                    drawnCards.addAll(cards.subList(i, cards.size))
                    break
                }
                drawnCards.add(cards[i])
                currentRevealIndex.value = i + 1
                delay(600) // Delay between card reveals
            }
            delay(500)
            showResults.value = true
        }
    }

    fun generatePackContents(series: BoosterSeries): List<Card> {
        // This would normally use the actual pack opening logic
        // For now, return placeholder cards from the series
        return when (series.code) {
            "genesis" -> com.creaturepacks.game.data.sample.GENESIS_CARDS.shuffled().take(5)
            "ember_reach" -> com.creaturepacks.game.data.sample.EMBER_REACH_CARDS.shuffled().take(5)
            "frost_veil" -> com.creaturepacks.game.data.sample.FROST_VEIL_CARDS.shuffled().take(5)
            "crystal_spire" -> com.creaturepacks.game.data.sample.CRYSTAL_SPIRE_CARDS.shuffled().take(5)
            else -> com.creaturepacks.game.data.sample.GENESIS_CARDS.shuffled().take(5)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background
        Box(
            modifier = Modifier.fillMaxSize()
                .background(CreatureColors.BackgroundDark)
        )

        if (isOpening.value) {
            // Pack Opening Animation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Pack illustration with animation
                Box(
                    modifier = Modifier.size(280.dp)
                ) {
                    androidx.compose.foundation.image.Image(
                        painter = painterResource(id = getPackDrawable(series.illustration)),
                        contentDescription = series.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleX = packScale.value
                                scaleY = packScale.value
                                rotationZ = packRotation.value
                            }
                    )
                    // Glow effect
                    if (packGlow.value > 0f) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(alpha = packGlow.value * 0.3f))
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                        )
                    }
                }

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Opening ${series.title}...",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CreatureColors.TextPrimary
                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

                androidx.compose.material3.LinearProgressIndicator(
                    progress = packGlow.value,
                    modifier = Modifier.fillMaxWidth().width(200.dp),
                    color = CreatureColors.Gold,
                    trackColor = CreatureColors.Divider
                )
            }
        } else if (!showResults.value) {
            // Card Reveal Animation
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Revealing Cards... ${currentRevealIndex.value}/5",
                    fontSize = 18.sp,
                    color = CreatureColors.TextSecondary
                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

                // Current card being revealed
                drawnCards.lastOrNull()?.let { card ->
                    RevealingCard(
                        card = card,
                        index = currentRevealIndex.value,
                        onAnimationEnd = { /* next card will be added automatically */ }
                    )
                }

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

                // Reveal all button
                if (!revealAllTriggered.value && drawnCards.size < 5) {
                    Button(
                        onClick = { revealAllTriggered.value = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CreatureColors.Purple,
                            contentColor = Color.White
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Reveal All",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            // Results Screen
            PackResultsScreen(
                cards = drawnCards.toList(),
                onClose = {
                    onCardsRevealed(drawnCards.toList())
                    onClose()
                }
            )
        }
    }
}

@Composable
fun RevealingCard(
    card: Card,
    index: Int,
    onAnimationEnd: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f)
    )
    val rotation by animateFloatAsState(
        targetValue = 0f,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 100f)
    )
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(400, delayMillis = 100)
    )
    val glowAlpha by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(1000, delayMillis = 200)
    )

    LaunchedEffect(Unit) {
        // Trigger entrance animation
        delay(50)
        onAnimationEnd()
    }

    Card(
        modifier = Modifier
            .width(220.dp)
            .height(320.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
                alpha = alpha
            },
        colors = CardDefaults.cardColors(
            containerColor = CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
        ) {
            // Rarity glow background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(card.rarity.toComposeColor().copy(alpha = glowAlpha * 0.3f))
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            )

            // Card artwork
            androidx.compose.foundation.image.Image(
                painter = painterResource(id = getArtDrawableForCard(card, true)),
                contentDescription = card.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(top = 12.dp, horizontal = 12.dp)
            )

            // Card info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreatureColors.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    RarityBadge(rarity = card.rarity)
                }

                Text(
                    text = card.description,
                    fontSize = 12.sp,
                    color = CreatureColors.TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    com.creaturepacks.game.ui.component.CreatureTypeIcon(
                        creatureType = card.type,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "#${card.collectorNumber}",
                        fontSize = 11.sp,
                        color = CreatureColors.TextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun PackResultsScreen(
    cards: List<Card>,
    onClose: () -> Unit
) {
    val anyRareOrBetter = cards.any { it.rarity.ordinal >= CardRarity.RARE.ordinal }
    val bestCard = cards.maxByOrNull { it.rarity.ordinal }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Pack Opened!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = CreatureColors.Gold
            )
            if (anyRareOrBetter) {
                Text(
                    text = "Rare find!",
                    fontSize = 16.sp,
                    color = CreatureColors.Positive
                )
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

        // Cards grid
        androidx.compose.foundation.lazy.LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.GridCells.Fixed(3),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(cards) { card ->
                ResultCard(card = card)
            }
        }

        // Summary
        if (bestCard != null) {
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = bestCard.rarity.toComposeDarkColor(),
                    contentColor = CreatureColors.TextPrimary
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Best Pull",
                        fontSize = 12.sp,
                        color = CreatureColors.TextMuted
                    )
                    Text(
                        text = bestCard.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreatureColors.TextPrimary
                    )
                    RarityBadge(rarity = bestCard.rarity)
                }
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CreatureColors.Gold,
                contentColor = CreatureColors.GoldDark
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ResultCard(card: Card) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f),
        colors = CardDefaults.cardColors(
            containerColor = CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .border(2.dp, card.rarity.toComposeColor(), androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Artwork
                androidx.compose.foundation.image.Image(
                    painter = painterResource(id = getArtDrawableForCard(card, true)),
                    contentDescription = card.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                )

                // Info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = card.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreatureColors.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    RarityBadge(rarity = card.rarity, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

fun getPackDrawable(name: String): Int = when (name) {
    "pack_genesis" -> R.drawable.pack_genesis
    "pack_ember" -> R.drawable.pack_ember
    "pack_frost" -> R.drawable.pack_frost
    "pack_crystal" -> R.drawable.pack_crystal
    else -> R.drawable.pack_genesis
}

fun getArtDrawableForCard(card: Card, isOwned: Boolean): Int = when (card.rarity) {
    CardRarity.COMMON -> R.drawable.art_template_common
    CardRarity.UNCOMMON -> R.drawable.art_template_uncommon
    CardRarity.RARE -> R.drawable.art_template_rare
    CardRarity.EPIC -> R.drawable.art_template_epic
    CardRarity.LEGENDARY -> R.drawable.art_template_legendary
    CardRarity.SECRET -> R.drawable.art_template_secret
}
