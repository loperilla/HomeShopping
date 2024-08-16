package io.loperilla.core_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 10/8/24 at 12:07
 * All rights reserved 2024
 */

@Composable
fun CommerceChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSelected: ImageVector = Icons.Filled.Done,
    modifier: Modifier = Modifier
) {
    val color = FilterChipDefaults.filterChipColors(
        selectedContainerColor = MaterialTheme.colorScheme.primary,
        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
    )
    FilterChip(
        onClick = {
            onClick()
        },
        label = { TextSemiBold(text) },
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = iconSelected,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            } else {
                null
            }
        },
        colors = color,
        selected = isSelected,
        modifier = modifier
    )
}

@PIXEL_33_NIGHT
@Composable
private fun CommerceChipPreview() {
    Screen {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CommerceChip(
                onClick = {},
                text = "Test",
                isSelected = false
            )
            CommerceChip(
                onClick = {},
                text = "Test",
                isSelected = true
            )
        }
    }
}