package io.loperilla.designsystem.composables.swipe

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
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.text.TextSemiBold
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem.composables.swipe
 * Created By Manuel Lopera on 16/3/25 at 16:04
 * All rights reserved 2025
 */

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
private fun SwipeBoxPrev() {
    Screen {
        val exampleList = mutableListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(exampleList.size) {
                SwipeBox(
                    endToStartSwipe = {
                        exampleList.removeAt(it)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    TextSemiBold(
                        text = "Item $it",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}