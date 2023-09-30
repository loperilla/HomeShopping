package io.loperilla.core_ui.alert_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.input.TextInput
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.alert_dialog
 * Created By Manuel Lopera on 30/9/23 at 10:04
 * All rights reserved 2023
 */

@Composable
fun CommonAlertDialog(
    alertIcon: ImageVector,
    alertTitle: String,
    dismissRequest: () -> Unit,
    acceptButtonAction: (String) -> Unit,
    acceptButtonText: String,
    cancelButtonText: String,
    modifier: Modifier = Modifier
) {
    var currentInput by remember { mutableStateOf("") }
    AlertDialog(
        icon = {
            Icon(
                imageVector = alertIcon,
                contentDescription = "ExitApp",
                tint = Color.White
            )
        },
        onDismissRequest = { dismissRequest() },
        title = { TextSemiBold(alertTitle, textColor = Color.White) },
        text = {
            TextInput(
                inputValue = currentInput,
                onValueChange = { newInput, isValid ->
                    currentInput = newInput
                },
                labelText = "nfknkdjnsd",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        },
        confirmButton = {
            TextButton(onClick = { acceptButtonAction(currentInput) }) {
                TextSemiBold(
                    acceptButtonText,
                    textSize = TextSmallSize,
                    textColor = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissRequest() }) {
                TextSemiBold(
                    cancelButtonText,
                    textSize = TextSmallSize,
                    textColor = Color.White
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        iconContentColor = MaterialTheme.colorScheme.inversePrimary,
        titleContentColor = MaterialTheme.colorScheme.inversePrimary,
        textContentColor = MaterialTheme.colorScheme.inversePrimary,
    )
}

@PIXEL_33_NIGHT
@Composable
fun CommonAlertDialogPrev() {
    Screen {
        CommonAlertDialog(
            alertIcon = Icons.Filled.AddBusiness,
            alertTitle = "Título",
            acceptButtonText = "crear",
            cancelButtonText = "eliminar",
            acceptButtonAction = {

            },
            dismissRequest = {

            }
        )
    }
}
