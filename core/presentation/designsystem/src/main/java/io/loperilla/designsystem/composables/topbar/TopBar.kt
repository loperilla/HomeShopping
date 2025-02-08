package io.loperilla.designsystem.composables.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.loperilla.designsystem.TextTitleSize
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.text.TextTitle

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
    navIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack
) {
    val colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
        containerColor = Color.Transparent
    )
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
            navIcon?.let {
                IconButton(onClick = navActionClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier,
        actions = actions
    )
}

@Preview
@Composable
private fun CommonTopbarPrev() {
    Screen {
        TransparentScaffold(
            topBar = {
                CommonTopBar(
                    topBarText = "Comercios",
                    navActionClick = {}
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                TextTitle(text = "hola hola")
            }
        }
    }
}