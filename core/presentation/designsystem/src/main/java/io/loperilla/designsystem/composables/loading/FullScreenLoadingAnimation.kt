package io.loperilla.designsystem.composables.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem.composables.loading
 * Created By Manuel Lopera on 12/4/25 at 10:27
 * All rights reserved 2025
 */

@Composable
fun AnimatedFullScreenLoading(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}