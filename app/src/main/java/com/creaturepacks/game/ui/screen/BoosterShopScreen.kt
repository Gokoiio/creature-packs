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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creaturepacks.game.data.sample.BOOSTER_SERIES
import com.creaturepacks.game.data.sample.BoosterSeries
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.component.CurrencyDisplay
import com.creaturepacks.game.ui.component.RarityBadge
import com.creaturepacks.game.ui.component.SectionHeader
import com.creaturepacks.game.ui.viewmodel.ShopViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creaturepacks.game.R
import com.creaturepacks.game.ui.util.getPackDrawable

@Composable
fun BoosterShopScreen(
    viewModel: ShopViewModel = viewModel(),
    onOpenPack: (com.creaturepacks.game.data.sample.BoosterSeries) -> Unit
) {
    val seriesList by viewModel.allSeries.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp) // Bottom nav space
    ) {
        // Header with currency
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Booster Shop",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreatureColors.TextPrimary
                    )
                    Text(
                        text = "Open packs to discover new creatures",
                        fontSize = 13.sp,
                        color = CreatureColors.TextMuted
                    )
                }
                CurrencyDisplay(credits = seriesList.firstOrNull()?.let { 
                    // We don't have direct access to credits here, but we could add it
                    0 
                } ?: 0)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp)
        ) {
            items(seriesList) { seriesUi ->
                val series = BOOSTER_SERIES.firstOrNull { it.code == seriesUi.code }
                BoosterShopCard(
                    series = seriesUi,
                    boosterSeries = series,
                    onClick = { viewModel.selectSeries(series) },
                    onPurchase = { 
                        if (series != null) {
                            onOpenPack(series)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BoosterShopCard(
    series: ShopViewModel.BoosterSeriesUi,
    boosterSeries: com.creaturepacks.game.data.sample.BoosterSeries?,
    onClick: () -> Unit,
    onPurchase: () -> Unit
) {
    val isLocked = !series.isUnlocked

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        onClick = if (!isLocked) onClick else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) CreatureColors.SurfaceCard.copy(alpha = 0.6f) else CreatureColors.SurfaceCard,
            contentColor = if (isLocked) CreatureColors.TextMuted else CreatureColors.TextPrimary
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isLocked) 1.dp else 6.dp)
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
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                androidx.compose.foundation.image.Image(
                    painter = painterResource(id = getPackDrawable(series.illustration)),
                    contentDescription = series.title,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .alpha(if (isLocked) 0.5f else 1f)
                )
                if (isLocked) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.filled.Lock,
                            contentDescription = "Locked",
                            tint = CreatureColors.TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            // Info
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
                        text = series.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLocked) CreatureColors.TextMuted else CreatureColors.TextPrimary
                    )
                    if (isLocked) {
                        RarityBadge(
                            rarity = com.creaturepacks.game.domain.model.CardRarity.COMMON,
                            showIcon = false
                        )
                    }
                }

                Text(
                    text = series.description,
                    fontSize = 12.sp,
                    color = CreatureColors.TextMuted,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurrencyDisplay(
                        credits = series.price,
                        showIcon = true
                    )
                    if (isLocked) {
                        Text(
                            text = "Locked",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = CreatureColors.TextMuted
                        )
                    } else if (!series.canAfford) {
                        Button(
                            onClick = onPurchase,
                            enabled = false,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CreatureColors.Divider,
                                contentColor = CreatureColors.TextMuted
                            ),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Not Enough",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        Button(
                            onClick = onPurchase,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CreatureColors.Gold,
                                contentColor = CreatureColors.GoldDark
                            ),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Open Pack",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
