package io.loperilla.designsystem.composables.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.text.TextSemiBold
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.button
 * Created By Manuel Lopera on 23/4/23 at 11:10
 * All rights reserved 2023
 */


@Composable
fun ShoppingButton(
    textButton: String,
    enableButton: Boolean = true,
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = Color.White,
        disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(
            alpha = 0.5f
        ),
        disabledContentColor = Color.White
    )
    Button(
        onClick = { onClickButton() },
        enabled = enableButton,
        modifier = modifier
            .fillMaxWidth(),
        colors = buttonColors
    ) {
        TextSemiBold(
            text = textButton
        )
    }
}

@Composable
fun IconShoppingButton(
    iconButton: ImageVector,
    modifier: Modifier = Modifier,
    enableButton: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = Color.White,
    disabledColor: Color = Color.White,
    disabledContainerColor: Color = MaterialTheme.colorScheme.secondary.copy(
        alpha = 0.5f
    ),
    onClickButton: () -> Unit,
) {
    val buttonColors = IconButtonDefaults.iconButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledColor
    )

    IconButton(
        onClick = { onClickButton() },
        enabled = enableButton,
        modifier = modifier,
        colors = buttonColors
    ) {
        Icon(
            imageVector = iconButton,
            contentDescription = null
        )
    }

}

@PIXEL_33_NIGHT
@Composable
fun FormButtonPrev() {
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                8.dp
            )
        ) {
            ShoppingButton(
                "Prueba",
            ) {

            }

            ShoppingButton(
                "Prueba",
            ) {

            }

            IconShoppingButton(
                iconButton = Icons.Default.PlusOne
            ) {

            }

        }
    }
}
