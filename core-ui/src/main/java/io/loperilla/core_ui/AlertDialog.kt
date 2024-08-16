package io.loperilla.core_ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 10/8/24 at 11:48
 * All rights reserved 2024
 */

@Composable
fun CommonAlertDialog(
    titleText: String,
    onConfirmButton: () -> Unit,
    onDismissClick: () -> Unit,
    alertIcon: ImageVector? = null,
    textContent: @Composable () -> Unit = {},
    onConfirmButtonText: String = stringResource(R.string.common_accept_button),
    onDismissButtonText: String = stringResource(R.string.common_cancel_button),
) {
    AlertDialog(
        icon = {
            alertIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "ExitApp"
                )
            }
        },
        onDismissRequest = { onDismissClick() },
        title = { TextSemiBold(titleText) },
        text = textContent,
        confirmButton = {
            TextButton(onClick = { onConfirmButton() }) {
                TextSemiBold(
                    onConfirmButtonText,
                    textSize = TextSmallSize,
                    textColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClick()}) {
                TextSemiBold(
                    onDismissButtonText,
                    textSize = TextSmallSize,
                    textColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    )
}

@Composable
fun FormAlertDialog(
    titleText: String,
    onConfirmButton: () -> Unit,
    onDismissClick: () -> Unit,
    onConfirmButtonText: String = stringResource(R.string.common_accept_button),
    onDismissButtonText: String = stringResource(R.string.common_cancel_button),
) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ })
}

@PIXEL_33_NIGHT
@Composable
private fun CommonAlertDialogPreview() {
    Screen {
        CommonAlertDialog(
            titleText = "Title",
            onConfirmButton = {},
            onDismissClick = {},
            onConfirmButtonText = "Confirm",
            onDismissButtonText = "Dismiss"
        )
    }
}