package io.loperilla.onboarding.addshoppingCart.select_commerce

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.itemPadding
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.onboarding.commerces

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.select_commerce
 * Created By Manuel Lopera on 25/8/24 at 11:16
 * All rights reserved 2024
 */

@Composable
fun SelectCommerceScreen(
    state: SelectCommerceState,
    onEvent: (SelectCommerceEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Screen {
        TransparentScaffold(
            topBar = {
                CommonTopBar(
                    topBarText = "Selecciona el comercio",
                    navActionClick = { onEvent(SelectCommerceEvent.OnBack) }
                )
            },
            modifier = modifier
        ) { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues
            ) {
                item {
                    TextSemiBold(
                        text = "Selecciona el comercio donde vayas a comprar",
                        modifier = Modifier.itemPadding()
                    )
                }
                items(
                    state.commerceList.size
                ) {
                    TextRegular(
                        text = state.commerceList[it].name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .itemPadding()
                            .clickable {
                                onEvent(SelectCommerceEvent.SelectCommerce(state.commerceList[it]))
                            }
                    )
                }
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun SelectCommerceScreenPrev() {
    SelectCommerceScreen(
        state = SelectCommerceState(commerceList = commerces),
        onEvent = {}
    )
}