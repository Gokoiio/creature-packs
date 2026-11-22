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
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.ui.component.CreatureColors
import com.creaturepacks.game.ui.component.CreatureTypeIcon
import com.creaturepacks.game.ui.component.RarityBadge
import com.creaturepacks.game.ui.component.SectionHeader
import com.creaturepacks.game.ui.viewmodel.CollectionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creaturepacks.game.R

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = viewModel(),
    onCardClick: (Card) -> Unit
) {
    val filteredCards by viewModel.filteredCards.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedRarity by viewModel.selectedRarity.collectAsStateWithLifecycle()
    val collectionProgress by viewModel.collectionProgress.collectAsStateWithLifecycle()

    var expandedMenu by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(searchQuery) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp) // Bottom nav space
    ) {
        // Header with search and filter
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Search field
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { newText ->
                        textFieldValue = newText
                        viewModel.setSearchQuery(newText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(44.dp),
                    label = { Text(text = "Search creatures", color = CreatureColors.TextMuted, fontSize = 14.sp) },
                    placeholder = { Text(text = "Search...", color = CreatureColors.TextMuted.copy(alpha = 0.6f), fontSize = 14.sp) },
                    singleLine = true,
                    colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = CreatureColors.SurfaceCard,
                        focusedContainerColor = CreatureColors.SurfaceElevated,
                        unfocusedBorderColor = CreatureColors.Divider,
                        focusedBorderColor = CreatureColors.Purple,
                        labelTextColor = CreatureColors.TextMuted,
                        placeholderTextColor = CreatureColors.TextMuted.copy(alpha = 0.6f),
                        textColor = CreatureColors.TextPrimary,
                        cursorColor = CreatureColors.Purple
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )

                // Rarity filter dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedMenu,
                    onExpandedChange = { expandedMenu = it },
                    modifier = Modifier.width(140.dp)
                ) {
                    androidx.compose.material3.Text(
                        text = selectedRarity?.displayName ?: "All Rarities",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = CreatureColors.TextPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .background(CreatureColors.SurfaceCard, androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                            .wrapContentSize(Alignment.Center)
                    )
                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "All Rarities", fontSize = 13.sp, color = CreatureColors.TextPrimary) },
                            onClick = {
                                viewModel.setRarityFilter(null)
                                expandedMenu = false
                            }
                        )
                        CardRarity.values().forEach { rarity ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RarityBadge(rarity = rarity, showIcon = true)
                                        Text(text = rarity.displayName, fontSize = 13.sp, color = CreatureColors.TextPrimary)
                                    }
                                },
                                onClick = {
                                    viewModel.setRarityFilter(rarity)
                                    expandedMenu = false
                                }
                            )
                        }
                    }
                }
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))

            // Collection progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Collection: ${collectionProgress.roundToInt()}%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = CreatureColors.TextSecondary
                )
                androidx.compose.material3.LinearProgressIndicator(
                    progress = collectionProgress / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = CreatureColors.Gold,
                    trackColor = CreatureColors.Divider,
                    strokeCap = true
                )
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))

        // Card grid
        if (filteredCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "No cards found",
                    fontSize = 16.sp,
                    color = CreatureColors.TextMuted,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
            ) {
                items(filteredCards) { card ->
                    CollectionCard(
                        card = card,
                        onClick = { onCardClick(card) }
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionCard(
    card: Card,
    onClick: () -> Unit
) {
    val isOwned = card.isOwned()
    val cardColor = if (isOwned) card.rarity.toComposeColor() else CreatureColors.SurfaceCard

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isOwned) card.rarity.toComposeDarkColor() else CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isOwned) 4.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Rarity badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Start
            ) {
                if (isOwned) {
                    RarityBadge(rarity = card.rarity)
                }
            }

            // Illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                androidx.compose.foundation.image.Image(
                    painter = painterResource(id = getArtDrawableForCard(card, isOwned)),
                    contentDescription = card.name,
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(if (isOwned) 1f else 0.4f)
                )
            }

            // Info footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = card.name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CreatureColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CreatureTypeIcon(creatureType = card.type, modifier = Modifier.size(20.dp))
                    if (card.quantityOwned > 1) {
                        androidx.compose.material3.Text(
                            text = "x${card.quantityOwned}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CreatureColors.Gold,
                            modifier = Modifier
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                .background(CreatureColors.GoldDark, androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                        )
                    }
                }
            }
        }
    }
}

fun getArtDrawableForCard(card: Card, isOwned: Boolean): Int = when (card.rarity) {
    CardRarity.COMMON -> R.drawable.art_template_common
    CardRarity.UNCOMMON -> R.drawable.art_template_uncommon
    CardRarity.RARE -> R.drawable.art_template_rare
    CardRarity.EPIC -> R.drawable.art_template_epic
    CardRarity.LEGENDARY -> R.drawable.art_template_legendary
    CardRarity.SECRET -> R.drawable.art_template_secret
}
