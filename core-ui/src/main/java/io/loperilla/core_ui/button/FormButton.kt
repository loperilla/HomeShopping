package io.loperilla.core_ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
            containerColor = Color(0xFF4EA8E9),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF78C8F9),
            disabledContentColor = Color.White
        )
    ) {
        Text(text = textButton)
    }
}