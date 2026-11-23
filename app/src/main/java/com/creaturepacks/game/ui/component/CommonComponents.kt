package com.creaturepacks.game.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.theme.toComposeColor
import com.creaturepacks.game.ui.theme.toComposeDarkColor
import com.creaturepacks.game.domain.model.CardRarity

@Composable
fun RarityBadge(
    rarity: CardRarity,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    val color = rarity.toComposeColor()
    val darkColor = rarity.toComposeDarkColor()

    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = darkColor,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx()),
                size = size
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showIcon) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.size(12.dp)
                ) {
                    drawCircle(
                        color = color,
                        radius = 6.dp.toPx(),
                        center = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)
                    )
                }
            }
            Text(
                text = rarity.displayName,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun CreatureTypeIcon(
    creatureType: com.creaturepacks.game.domain.model.CreatureType,
    modifier: Modifier = Modifier.size(28.dp)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(creatureType.color))
    ) {
        Text(
            text = creatureType.displayName.substring(0, 1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun CurrencyDisplay(
    credits: Int,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .background(CreatureColors.ChipBg, RoundedCornerShape(20.dp)),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon) {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(18.dp)) {
                drawCircle(
                    color = CreatureColors.Gold,
                    radius = 9.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(9.dp.toPx(), 9.dp.toPx())
                )
                drawCircle(
                    color = CreatureColors.GoldDark,
                    radius = 5.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(9.dp.toPx(), 9.dp.toPx())
                )
            }
        }
        Text(
            text = credits.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = CreatureColors.TextPrimary
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    iconPainter: Painter? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconPainter != null) {
                Image(
                    painter = iconPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(32.dp)
                        .background(CreatureColors.ChipBg, RoundedCornerShape(8.dp))
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CreatureColors.TextPrimary
                )
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = CreatureColors.TextMuted
                )
            }
        }
    }
}

@Composable
fun AnimatedCard(
    modifier: Modifier = Modifier,
    rarity: CardRarity,
    content: @Composable () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = rarity.toComposeColor(),
        animationSpec = androidx.compose.animation.spring(dampingRatio = 0.8f)
    )
    val elevation by animateDpAsState(
        targetValue = 4.dp,
        animationSpec = androidx.compose.animation.spring(dampingRatio = 0.8f)
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = CreatureColors.SurfaceCard,
            contentColor = CreatureColors.TextPrimary
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, borderColor, RoundedCornerShape(16.dp))
        ) {
            content()
        }
    }
}

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 200.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(CreatureColors.SurfaceCard, RoundedCornerShape(16.dp))
    ) {
        // Placeholder for skeleton loading
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    action: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CreatureColors.TextPrimary
            )
            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = CreatureColors.TextMuted
                )
            }
        }
        action?.invoke()
    }
}

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CreatureColors.TextMuted.copy(alpha = 0.5f),
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CreatureColors.TextSecondary
            )
            Text(
                text = message,
                fontSize = 14.sp,
                color = CreatureColors.TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}
