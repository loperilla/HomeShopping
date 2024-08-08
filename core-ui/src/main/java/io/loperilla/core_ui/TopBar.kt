package io.loperilla.core_ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.loperilla.core_ui.text.TextTitle

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 8/8/24 at 19:00
 * All rights reserved 2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    topBarText: String,
    navActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    topBarTextColor: Color = Color.Unspecified,
    navIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
) {
    TopAppBar(
        title = {
            if (topBarText.isNotEmpty()) {
                TextTitle(
                    text = topBarText,
                    textSize = TextTitleSize,
                    textColor = topBarTextColor
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navActionClick) {
                Icon(
                    imageVector = navIcon,
                    contentDescription = "Back"
                )
            }
        },
        modifier = modifier,
        actions = actions
    )
}