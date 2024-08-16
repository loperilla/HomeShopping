package io.loperilla.core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 6/9/23 at 19:41
 * All rights reserved 2023
 */

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    HomeShoppingTheme {
        Surface(
            color = Color.Transparent,
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to MaterialTheme.colorScheme.background,
                        1.0f to MaterialTheme.colorScheme.primary,
                        startY = 0.0f,
                        endY = 1500.0f
                    )
                )
        ) {
            content()
        }
    }
}

@Composable
fun TransparentScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = Color.Transparent,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar,
        bottomBar,
        snackbarHost,
        floatingActionButton,
        floatingActionButtonPosition,
        containerColor,
        contentColor,
        contentWindowInsets,
        content
    )
}

@PIXEL_33_NIGHT
@Composable
fun ScreenPrev() {
    Screen {
        Column {
            Text("Soy una screen")
        }
    }
}
