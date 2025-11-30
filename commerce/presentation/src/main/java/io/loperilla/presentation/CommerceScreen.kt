package io.loperilla.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.card.HomeShoppingCard
import io.loperilla.designsystem.composables.input.TextInput
import io.loperilla.designsystem.composables.loading.AnimatedFullScreenLoading
import io.loperilla.designsystem.composables.swipe.SwipeBox
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.testing.tag.COMMERCE_EMPTY_LIST
import io.loperilla.testing.tag.COMMERCE_FAB
import io.loperilla.testing.tag.COMMERCE_LOADING
import io.loperilla.testing.tag.COMMERCE_ROOT_SCREEN
import io.loperilla.testing.tag.COMMERCE_SCREEN
import io.loperilla.testing.tag.COMMERCE_TOPBAR
import io.loperilla.testing.tag.NEW_COMMERCE_INPUT
import io.loperilla.testing.tag.COMMERCE_LIST

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
    TransparentScaffold(
        topBar = {
            if (!state.isLoading) {
                CommonTopBar(
                    topBarText = "Comercios",
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    navActionClick = {
                        onEvent(CommerceEvent.GoBack)
                    },
                    modifier = Modifier
                        .testTag(COMMERCE_TOPBAR)
                )
            }
        },
        floatingActionButton = {
            if (!state.isLoading) {
                FloatingActionButton(
                    onClick = {
                        onEvent(CommerceEvent.AddNewCommerce)
                    },
                    modifier = Modifier
                        .testTag(COMMERCE_FAB)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new commerce"
                    )
                }
            }
        },
        modifier = modifier
            .testTag(COMMERCE_ROOT_SCREEN)
    ) {
        AnimatedFullScreenLoading(
            isLoading = state.isLoading,
            Modifier
                .padding(it)
                .testTag(COMMERCE_LOADING)
        )
        Column(
            modifier = Modifier
                .padding(it)
                .testTag(COMMERCE_SCREEN)
                .fillMaxSize(),
        ) {
            AnimatedVisibility(state.showNewCommerceInput) {
                TextInput(
                    text = state.newCommerceName,
                    onTextChange = { newValue ->
                        onEvent(CommerceEvent.NewCommerceNameChanged(newValue))
                    },
                    labelText = "Nuevo comercio",
                    imeAction = ImeAction.Done,
                    onKeyBoardDoneAction = {
                        onEvent(CommerceEvent.SendNewCommerce)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .testTag(NEW_COMMERCE_INPUT)
                )
            }
            CommerceListScreen(state, onEvent)
            EmptyCommerceListScreen(state, onEvent)
        }
    }
}

@Composable
private fun CommerceListScreen(
    state: CommerceState,
    onEvent: (CommerceEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(!state.isLoading && state.commerceList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .testTag(COMMERCE_LIST)
                .padding(16.dp),
        ) {
            items(state.commerceList.size) { index ->
                CommerceItem(
                    commerce = state.commerceList[index],
                    onEvent = onEvent,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CommerceItem(
    commerce: Commerce,
    onEvent: (CommerceEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeBox(
        endToStartSwipe = {
            onEvent(CommerceEvent.RemoveCommerce(commerce.id))
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HomeShoppingCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextTitle(
                commerce.name,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun EmptyCommerceListScreen(
    state: CommerceState,
    onEvent: (CommerceEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(!state.isLoading && state.commerceList.isEmpty()) {
        HomeShoppingCard(
            modifier = modifier
                .fillMaxWidth()
                .testTag(COMMERCE_EMPTY_LIST)
                .wrapContentHeight()
                .clickable {
                    onEvent(CommerceEvent.AddNewCommerce)
                }
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextTitle(
                    "No hay comercios disponibles",
                    modifier = Modifier
                        .padding(16.dp)
                )

                Icon(
                    imageVector = Icons.Default.NoteAlt,
                    contentDescription = "Create new shopping list",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun CommerceScreenPrev() {
    Screen {
        CommerceScreen(
            state = CommerceState(
                isLoading = false,
                commerceList = listOf(
                    Commerce(
                        id = "1",
                        name = "Comercio 1"
                    ),
                    Commerce(
                        id = "2",
                        name = "Comercio 2"
                    ),
                    Commerce(
                        id = "3",
                        name = "Comercio 3"
                    )
                )
            ),
            onEvent = {}
        )
    }
}

@PIXEL_33_NIGHT
@Composable
private fun CommerceScreenPreviewEmptyList() {
    Screen {
        CommerceScreen(
            state = CommerceState(
                isLoading = false,
                showNewCommerceInput = true
            ),
            onEvent = {}
        )
    }
}