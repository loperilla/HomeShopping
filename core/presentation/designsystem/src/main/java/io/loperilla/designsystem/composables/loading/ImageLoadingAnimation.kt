package io.loperilla.designsystem.composables.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem.composables.loading
 * Created By Manuel Lopera on 9/3/25 at 17:55
 * All rights reserved 2025
 */

@Composable
fun ImageLoadingAnimation() {
    val animation = rememberInfiniteTransition(label = "")
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    )
    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                5.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
    )
}