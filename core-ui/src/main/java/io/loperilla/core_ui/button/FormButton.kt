package io.loperilla.core_ui.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT

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
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = textButton)
    }
}

@PIXEL_33_NIGHT
@Composable
fun FormButtonPrev() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
}
