package io.loperilla.onboarding.addshoppingCart.select_commerce

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.loperilla.onboarding.commerces
import io.loperilla.ui.CommonTopBar
import io.loperilla.ui.HomeShoppingCard
import io.loperilla.ui.Screen
import io.loperilla.ui.TransparentScaffold
import io.loperilla.ui.itemPadding
import io.loperilla.ui.previews.PIXEL_33_NIGHT
import io.loperilla.ui.text.TextRegular
import io.loperilla.ui.text.TextSemiBold

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
    io.loperilla.ui.Screen {
        io.loperilla.ui.TransparentScaffold(
            topBar = {
                io.loperilla.ui.CommonTopBar(
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
                    io.loperilla.ui.text.TextSemiBold(
                        text = "Selecciona el comercio donde vayas a comprar",
                        modifier = Modifier.itemPadding()
                    )
                }
                items(
                    state.commerceList.size
                ) {
                    io.loperilla.ui.HomeShoppingCard(
                        modifier = Modifier
                            .itemPadding()
                    ) {
                        io.loperilla.ui.text.TextRegular(
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
}

@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
private fun SelectCommerceScreenPrev() {
    SelectCommerceScreen(
        state = SelectCommerceState(commerceList = commerces),
        onEvent = {}
    )
}