package io.loperilla.designsystem.composables.tab

import androidx.compose.runtime.Composable

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.tab
 * Created By Manuel Lopera on 2/9/23 at 11:10
 * All rights reserved 2023
 */
data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)
