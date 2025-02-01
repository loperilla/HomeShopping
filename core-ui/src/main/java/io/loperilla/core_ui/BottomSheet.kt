package io.loperilla.core_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 28/8/24 at 10:36
 * All rights reserved 2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeShoppingBottomSheet(
    title: String,
    items: List<ItemBottomSheet>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    ModalBottomSheet(
        shape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
        onDismissRequest = { onDismiss.invoke() },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextSemiBold(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = title,
                textAlign = TextAlign.Center
            )
            items.forEach {item ->
                ListItem(
                    modifier = Modifier.clickable {
                        item.onClick.invoke()
                    },
                    headlineContent = {
                        TextRegular(
                            text = item.title,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}

data class ItemBottomSheet(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@PIXEL_33_NIGHT
@Composable
private fun HomeShoppingBottomSheetPreview() {
    Screen {
        HomeShoppingBottomSheet(
            title = "Title",
            items = listOf(
                ItemBottomSheet(
                    title = "Item 1",
                    icon = Icons.Filled.Done,
                    onClick = {}
                ),
                ItemBottomSheet(
                    title = "Item 2",
                    icon = Icons.Filled.Done,
                    onClick = {}
                )
            ),
            onDismiss = {}
        )
    }
}