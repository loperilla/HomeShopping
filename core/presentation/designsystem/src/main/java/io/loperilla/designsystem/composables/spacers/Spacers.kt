package io.loperilla.designsystem.composables.spacers

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.spacers
 * Created By Manuel Lopera on 7/9/23 at 21:11
 * All rights reserved 2023
 */

@Composable
fun LowSpacer(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    )
}

@Composable
fun MediumSpacer(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
    )
}

@Composable
fun ColumnScope.FullWeightSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
    )
}
