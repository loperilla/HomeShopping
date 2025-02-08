package io.loperilla.onboarding.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import io.loperilla.onboarding.FlowCommerce
import io.loperilla.onboarding.commerces
import io.loperilla.onboarding_presentation.R
import io.loperilla.ui.CommonAlertDialog
import io.loperilla.ui.CommonTopBar
import io.loperilla.ui.MEDIUM
import io.loperilla.ui.Screen
import io.loperilla.ui.TransparentScaffold
import io.loperilla.ui.previews.PIXEL_33_NIGHT
import io.loperilla.ui.text.TextTitle

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 20/8/23 at 18:58
 * All rights reserved 2023
 */

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    io.loperilla.ui.Screen {
        io.loperilla.ui.TransparentScaffold(
            topBar = {
                io.loperilla.ui.CommonTopBar(
                    stringResource(R.string.home_scaffold_title),
                    navActionClick = {},
                    navIcon = null,
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
                    onClick = { onEvent(HomeEvent.CreateShoppingBasket) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.fab_createshoppingcart_content_description)
                    )
                }
            }
        ) { paddingValues ->
            AnimatedVisibility(visible = state.showAreYouSureLogout) {
                io.loperilla.ui.CommonAlertDialog(
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
    val rotationState by animateFloatAsState(
        targetValue = if (state.commerceListIsVisible) 180f else 0f, label = ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(io.loperilla.ui.MEDIUM)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            io.loperilla.ui.text.TextTitle(
                text = "Comercios",
            )

            Row {
                IconButton(onClick = { onEvent(HomeEvent.NavigateToCommerce) }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Icon add commerce")
                }
                IconButton(onClick = { onEvent(HomeEvent.ChangeChipVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Icon show chips",
                        modifier = Modifier
                            .rotate(rotationState)
                    )
                }
            }
        }
        AnimatedVisibility(visible = state.commerceListIsVisible) {
            FlowCommerce(
                state.commerceList,
                onCommerceClicked = {
                    onEvent(HomeEvent.ItemSelected(it.id))
                }
            )
        }
    }
}
@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
fun EmptyShoppingBuyListPrev() {
    io.loperilla.ui.Screen {
        HomeScreen(
            state = HomeState(),
            onEvent = {}
        )
    }
}

@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
private fun CommerceVisibleListPreview() {
    io.loperilla.ui.Screen {
        CommerceList(
            state = HomeState(
                commerceList = commerces,
                commerceListIsVisible = true
            ),
            onEvent = {}
        )
    }
}
