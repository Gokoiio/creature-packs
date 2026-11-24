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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.component.CurrencyDisplay
import com.creaturepacks.game.ui.component.SectionHeader
import com.creaturepacks.game.ui.component.StatCard
import com.creaturepacks.game.ui.viewmodel.PlayerViewModel
import com.creaturepacks.game.ui.viewmodel.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creaturepacks.game.R

@Composable
fun ProfileScreen(
    playerViewModel: PlayerViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val stats by playerViewModel.playerStats.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp) // Bottom nav space
    ) {
        // Profile header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Trainer Profile",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = CreatureColors.TextPrimary
                        )
                        Text(
                            text = "Level ${stats.level}",
                            fontSize = 16.sp,
                            color = CreatureColors.Purple
                        )
                    }
                    CurrencyDisplay(credits = stats.credits)
                }

                // XP Progress
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Experience",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = CreatureColors.TextSecondary
                        )
                        Text(
                            text = "${stats.experience} / ${stats.level * 1000} XP",
                            fontSize = 12.sp,
                            color = CreatureColors.TextMuted
                        )
                    }
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = (stats.experience % 1000).toFloat() / 1000f,
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = CreatureColors.Purple,
                        trackColor = CreatureColors.Divider,
                        strokeCap = true
                    )
                }
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

        // Statistics cards
        SectionHeader(title = "Statistics")

        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp)
            ) {
                items(listOf(
                    StatData("Packs Opened", stats.packsOpened.toString(), R.drawable.ic_launcher),
                    StatData("Rare Discoveries", stats.rareDiscoveries.toString(), R.drawable.ic_launcher),
                    StatData("Cards Owned", stats.cardsOwned.toString(), R.drawable.ic_launcher),
                    StatData("Unique Cards", "${stats.uniqueCards} / ${stats.totalCardsInSet}", R.drawable.ic_launcher),
                    StatData("Collection Complete", "${stats.collectionPercent().roundToInt()}%", R.drawable.ic_launcher)
                )) { stat ->
                    StatCard(
                        title = stat.title,
                        value = stat.value,
                        iconPainter = painterResource(id = stat.iconRes)
                    )
                }
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))

        // Achievements
        SectionHeader(title = "Achievements")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CreatureColors.SurfaceCard,
                contentColor = CreatureColors.TextPrimary
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(120.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material.icons.filled.EmojiEvents,
                    contentDescription = "Achievements",
                    tint = CreatureColors.Gold,
                    modifier = Modifier
                        .size(48.dp)
                        .wrapContentSize(Alignment.Center)
                )
                Text(
                    text = "Achievements",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CreatureColors.TextPrimary
                )
                Text(
                    text = "Complete milestones to unlock achievements. More coming soon!",
                    fontSize = 12.sp,
                    color = CreatureColors.TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

        // Settings
        SectionHeader(title = "Settings")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CreatureColors.SurfaceCard,
                contentColor = CreatureColors.TextPrimary
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingRow(
                    title = "Sound Effects",
                    subtitle = "Play sound effects when opening packs",
                    icon = androidx.compose.material.icons.filled.VolumeUp,
                    checked = stats.soundEnabled,
                    onCheckedChange = { settingsViewModel.setSoundEnabled(it) }
                )
                androidx.compose.foundation.layout.Divider(
                    color = CreatureColors.Divider,
                    thickness = 1.dp
                )
                SettingRow(
                    title = "Background Music",
                    subtitle = "Play ambient music in menus",
                    icon = androidx.compose.material.icons.filled.MusicNote,
                    checked = stats.musicEnabled,
                    onCheckedChange = { settingsViewModel.setMusicEnabled(it) }
                )
            }
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CreatureColors.TextMuted,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = CreatureColors.TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = CreatureColors.TextMuted
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.SwitchDefaults.colors(
                checkedThumbColor = CreatureColors.Gold,
                checkedTrackColor = CreatureColors.Gold.copy(alpha = 0.3f),
                uncheckedThumbColor = CreatureColors.TextMuted,
                uncheckedTrackColor = CreatureColors.Divider
            )
        )
    }
}

data class StatData(
    val title: String,
    val value: String,
    val iconRes: Int
)
