package io.loperilla.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 16/3/25 at 12:37
 * All rights reserved 2025
 */

@Composable
fun CommerceScreen(
    state: CommerceState,
    onEvent: (CommerceEvent) -> Unit,
    modifier: Modifier = Modifier
) {

}

@PIXEL_33_NIGHT
@Composable
private fun CommerceScreenPrev() {
    Screen {
        CommerceScreen(
            state = CommerceState(),
            onEvent = {}
        )
    }
}