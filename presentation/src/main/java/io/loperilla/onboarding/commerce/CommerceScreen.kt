package io.loperilla.onboarding.commerce

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.commerce
 * Created By Manuel Lopera on 11/8/24 at 19:44
 * All rights reserved 2024
 */

@Composable
fun CommerceScreen(
    state: CommerceState,
    onEvent: (CommerceEvent) -> Unit
) {
    BackHandler {
        onEvent(CommerceEvent.GoBack)
    }
    Screen {
        TransparentScaffold(
            topBar = {
                CommonTopBar(
                    topBarText = "Comercios",
                    navActionClick = {
                        onEvent(CommerceEvent.GoBack)
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.list.size) {
                    SwipeBox(
                        endToStartSwipe = {
                            onEvent(
                                CommerceEvent.DeleteCommerce(
                                    state.list[it].id
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        TextSemiBold(
                            text = state.list[it].name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    endToStartSwipe: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    lateinit var icon: ImageVector
    lateinit var alignment: Alignment
    val color: Color

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.errorContainer
        }

        else -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = Color.Unspecified
        }
    }

    SwipeToDismissBox(
        modifier = modifier
            .animateContentSize(),
        state = swipeState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color,
                        shape = RoundedCornerShape(25f)
                    )
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(swipeState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
                endToStartSwipe()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        else -> {}
    }
}

@PIXEL_33_NIGHT
@Composable
private fun CommerceScreenPrev() {
    Screen {
        CommerceScreen(
            state = CommerceState(
                list = listOf(
                    Commerce("1", "MasYMas", true),
                    Commerce("2", "Mercadona", false),
                    Commerce("3", "Carrefour", false),
                )
            ),
            onEvent = {}
        )
    }
}