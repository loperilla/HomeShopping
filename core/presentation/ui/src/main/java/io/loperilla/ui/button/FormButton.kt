package io.loperilla.ui.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.loperilla.ui.Screen
import io.loperilla.ui.previews.PIXEL_33_NIGHT
import io.loperilla.ui.text.TextSemiBold

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.button
 * Created By Manuel Lopera on 23/4/23 at 11:10
 * All rights reserved 2023
 */

@Composable
fun FormButton(
    textButton: String,
    enableButton: Boolean,
    modifier: Modifier = Modifier,
    onClickButton: () -> Unit,
) {
    Button(
        onClick = { onClickButton() },
        enabled = enableButton,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = 0.5f
            ),
            disabledContentColor = Color.White
        )
    ) {
        TextSemiBold(
            text = textButton
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
            FormButton(
                "Prueba",
                true,
            ) {

            }

            FormButton(
                "Prueba",
                false,
            ) {

            }

            
        }
    }
}
