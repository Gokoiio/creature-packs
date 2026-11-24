package com.creaturepacks.game.ui.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creaturepacks.game.data.sample.BOOSTER_SERIES
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.component.CurrencyDisplay
import com.creaturepacks.game.ui.component.RarityBadge
import com.creaturepacks.game.ui.component.SectionHeader
import com.creaturepacks.game.ui.component.StatCard
import com.creaturepacks.game.ui.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creaturepacks.game.ui.theme.CreaturePacksTheme
import com.creaturepacks.game.R
import com.creaturepacks.game.ui.util.getPackDrawable

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToShop: () -> Unit,
    onNavigateToCollection: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val stats by viewModel.playerStats.collectAsStateWithLifecycle()

    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.TopStart
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with currency
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome back, Trainer",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreatureColors.TextPrimary
                    )
                    Text(
                        text = "Level ${stats.level}  •  ${stats.collectionPercent().roundToInt()}% Complete",
                        fontSize = 13.sp,
                        color = CreatureColors.TextMuted
                    )
                }
                CurrencyDisplay(credits = stats.credits)
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

            // Player stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Packs Opened",
                    value = stats.packsOpened.toString()
                )
                StatCard(
                    title = "Rare Finds",
                    value = stats.rareDiscoveries.toString()
                )
                StatCard(
                    title = "Collection",
                    value = "${stats.collectionPercent().roundToInt()}%"
                )
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(20.dp))

            // Daily reward
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = CreatureColors.SurfaceCard,
                    contentColor = CreatureColors.TextPrimary
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .height(100.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Daily Reward",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CreatureColors.TextPrimary
                            )
                            Text(
                                text = "Claim your free credits",
                                fontSize = 12.sp,
                                color = CreatureColors.TextMuted
                            )
                        }
                        if (!stats.tutorialCompleted) {
                            androidx.compose.material3.Button(
                                onClick = { viewModel.claimDailyReward() },
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = CreatureColors.Gold,
                                    contentColor = CreatureColors.GoldDark
                                ),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Claim 50",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            Text(
                                text = "Claimed",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = CreatureColors.Positive
                            )
                        }
                    }
                }
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(20.dp))

            // Featured boosters
            SectionHeader(
                title = "Featured Boosters",
                subtitle = "Tap to view in shop"
            ) {
                androidx.compose.material3.TextButton(
                    onClick = onNavigateToShop
                ) {
                    Text(
                        text = "View All",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CreatureColors.Purple
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
            ) {
                items(viewModel.featuredBoosters) { series ->
                    BoosterPreviewCard(
                        series = series,
                        onClick = { onNavigateToShop() }
                    )
                }
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(100.dp)) // Bottom nav space
        }
    }
}

@Composable
fun BoosterPreviewCard(
    series: com.creaturepacks.game.data.sample.BoosterSeries,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(280.dp)
            .fillMaxHeight(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Pack illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                androidx.compose.foundation.image.Image(
                    painter = painterResource(id = getPackDrawable(series.illustration)),
                    contentDescription = series.title,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            // Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = series.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CreatureColors.TextPrimary
                )
                Text(
                    text = series.description,
                    fontSize = 11.sp,
                    color = CreatureColors.TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurrencyDisplay(
                        credits = series.price,
                        showIcon = true,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    if (series.isUnlocked) {
                        androidx.compose.material3.Text(
                            text = "Tap to Open",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = CreatureColors.Purple
                        )
                    } else {
                        RarityBadge(
                            rarity = com.creaturepacks.game.domain.model.CardRarity.COMMON,
                            showIcon = false
                        ) // Reuse for locked state
                    }
                }
            }
        }
    }
}
