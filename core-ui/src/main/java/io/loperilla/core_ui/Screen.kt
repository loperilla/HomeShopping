package io.loperilla.core_ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            modifier = modifier
                .fillMaxSize()
        ) {
            content()
        }
    }
}

@PIXEL_33_NIGHT
@Composable
fun ScreenPrev() {
    Screen {
        Text("Soy una screen")
    }
}
