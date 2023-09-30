package io.loperilla.core_ui.spinner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.spinner
 * Created By Manuel Lopera on 23/9/23 at 17:21
 * All rights reserved 2023
 */

@Composable
fun Spinner(
    isExpanded: Boolean,
    dropdownItems: List<String>,
    newItemSelected: (Int) -> Unit,
    changeExpandedEvent: (Boolean) -> Unit,
    trailingIconOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { changeExpandedEvent(true) })
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextSemiBold(
                    text = dropdownItems.getOrNull(selectedIndex) ?: "No item selected",
                    textSize = TextSmallSize,
                    modifier = Modifier
                        .padding(MEDIUM)
                        .weight(1f)
                )

                IconButton(onClick = { trailingIconOnClick() }) {
                    Icon(
                        Icons.Filled.Add,
                        null
                    )
                }
            }


            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { changeExpandedEvent(false) },
            ) {
                dropdownItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
                            newItemSelected(index)
                            changeExpandedEvent(false)
                        },
                        text = {
                            TextRegular(text = item)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM)
                    )
                }
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
fun SpinnerDemoPrev() {
    Screen {
        val items = listOf("Item 1", "Item 2", "Item 3")
        var expanded by remember { mutableStateOf(true) }
        Spinner(
            isExpanded = expanded,
            dropdownItems = items,
            newItemSelected = { indexSelected ->

            },
            changeExpandedEvent = { newExpandedValue ->

            },
            modifier = Modifier,
            trailingIconOnClick = {

            }
        )
    }
}