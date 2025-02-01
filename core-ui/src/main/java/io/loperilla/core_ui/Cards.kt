package io.loperilla.core_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 26/8/24 at 19:39
 * All rights reserved 2024
 */

@Composable
fun HomeShoppingCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val cardColor = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    )
    ElevatedCard(
        modifier = modifier,
        colors = cardColor
    ) {
        content()
    }
}

@PIXEL_33_NIGHT
@Composable
private fun HomeShoppingCardPrev() {
    Screen {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) {
                HomeShoppingCard(
                    modifier = Modifier
                        .height(200.dp)
                ) {
                    Text(text = "soy $it un texto dentro de un card")
                }
            }
        }
    }
}