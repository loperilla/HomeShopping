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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.card.HomeShoppingCard
import io.loperilla.designsystem.composables.spacers.MediumSpacer
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.domain.model.dummy.dummyUser
import kotlinx.coroutines.launch

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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    MediumSpacer()
                    navigationItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = { TextTitle(text = item.title) },
                            selected = index == selectedItemIndex,
                            onClick = {

                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                                onEvent(item.onClick)
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
                        )
                    }
                }
            }
        }
    ) {
        TransparentScaffold(
            topBar = {
                if (!state.isLoading) {
                    CommonTopBar(
                        topBarText = "Bienvenido, ${state.userName}",
                        navIcon = Icons.Default.Menu,
                        navActionClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
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
                AnimatedVisibility(state.showUserIncompleteUser) {
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
                                    text = "Usuario incompleto, por favor completa tu información",
                                    textSize = 16.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = "Create new shopping list",
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                            }
                        }
                    )
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
                                Icon(
                                    imageVector = Icons.Default.NoteAlt,
                                    contentDescription = "Create new shopping list",
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

data class NavigationItems(
    val title: String,
    val icon: ImageVector,
    val onClick: HomeEvent
)

val navigationItems = listOf(
    NavigationItems(
        title = "Cerrar sesión",
        icon = Icons.AutoMirrored.Filled.Logout,
        onClick = HomeEvent.LogOut
    )
)


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

@PIXEL_33_NIGHT
@Composable
private fun HomeScreenIncompleteUserPrev() {
    Screen {
        HomeScreen(
            state = HomeState(
                false,
                null,
                null
            ),
            onEvent = {}
        )
    }
}
