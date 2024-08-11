package io.loperilla.onboarding.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.loperilla.core_ui.CommerceChip
import io.loperilla.core_ui.CommonAlertDialog
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextTitle
import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_presentation.R

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 20/8/23 at 18:58
 * All rights reserved 2023
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Screen {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        TextTitle(
                            stringResource(R.string.home_scaffold_title)
                        )
                    },
                    actions = {
                        IconButton(onClick = { onEvent(HomeEvent.ShowLogoutDialog) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = stringResource(R.string.logout_icon_content_description)
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onEvent(HomeEvent.GoToShoppingBasket) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.fab_createshoppingcart_content_description)
                    )
                }
            }
        ) { paddingValues ->
            AnimatedVisibility(visible = state.showAreYouSureLogout) {
                CommonAlertDialog(
                    titleText = stringResource(R.string.logout_dialog_title),
                    onConfirmButton = { onEvent(HomeEvent.LogOut) },
                    onDismissClick = { onEvent(HomeEvent.HideLogoutDialog) }
                )
            }
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                CommerceList(
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun CommerceList(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MEDIUM)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextTitle(
                text = "Comercios",
            )

            Row {
                IconButton(onClick = { onEvent(HomeEvent.AddCommerce) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Icon add commerce")
                }
                IconButton(onClick = { onEvent(HomeEvent.ChangeChipVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Icon show chips",
                        modifier = Modifier
                            .rotate(
                                if (!state.commerceListIsVisible) {
                                    180f
                                } else {
                                    0f
                                }
                            )
                    )
                }
            }
        }
        AnimatedVisibility(visible = state.commerceListIsVisible) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MEDIUM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(state.commerceList.size) {
                    CommerceChip(
                        text = state.commerceList[it].name,
                        isSelected = state.commerceList[it].isSelected,
                        onClick = {
                            onEvent(HomeEvent.ItemSelected(state.commerceList[it].id))
                        }
                    )
                }
            }
        }
    }

}


@Composable
fun EmptyShoppingBuyList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MEDIUM),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = stringResource(R.string.empty_shopping_cart_icon_content_description),
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = stringResource(R.string.empty_shopping_cart_text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun EmptyShoppingBuyListPrev() {
    Screen {
        HomeScreen(
            state = HomeState(),
            onEvent = {}
        )
    }
}

@PIXEL_33_NIGHT
@Composable
private fun CommerceVisibleListPreview() {
    Screen {
        CommerceList(
            state = HomeState(
                commerceList = listOf(
                    Commerce("1", "MasYMas", true),
                    Commerce("2", "Mercadona", false),
                    Commerce("3", "Carrefour", false),
                ),
                commerceListIsVisible = true
            ),
            onEvent = {}
        )
    }
}
