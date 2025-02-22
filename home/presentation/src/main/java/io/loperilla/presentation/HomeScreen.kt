package io.loperilla.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.button.IconShoppingButton
import io.loperilla.designsystem.composables.card.HomeShoppingCard
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.SimpleTopbar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.domain.model.dummy.dummyUser

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 19/2/25 at 19:58
 * All rights reserved 2025
 */

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TransparentScaffold(
        topBar = {
            if (!state.isLoading) {
                SimpleTopbar(
                    title = "Bienvenido, ${state.userName}",
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            AnimatedVisibility(state.showNotExistLastShoppingList) {
                HomeShoppingCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            onEvent(HomeEvent.OnClickCreateNewShoppingList)
                        }
                        .padding(16.dp),
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                        ) {
                            TextTitle(
                                text = "No tienes ninguna lista de compra",
                                textSize = 16.sp
                            )
                            IconShoppingButton(
                                iconButton = Icons.Default.PlusOne,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {

                            }
                        }
                    }
                )
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun HomeScreenPrev() {
    Screen {
        HomeScreen(
            state = HomeState(
                false,
                null,
                dummyUser
            ),
            onEvent = {}
        )
    }
}
