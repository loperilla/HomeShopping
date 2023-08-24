package io.loperilla.core_ui.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.input.InputValidators.isEmailValid
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 22/4/23 at 20:33
 * All rights reserved 2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    inputValue: String,
    modifier: Modifier = Modifier,
    placeholderValue: String = "correo@dominio.com",
    onValueChange: (String, Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LOW),
        value = inputValue,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "emailIcon")
        },
        placeholder = {
            Text(text = placeholderValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = {
            onValueChange(
                it,
                isEmailValid(it)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            focusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@PIXEL_33_NIGHT
@Composable
fun EmailInputPreview() {
    HomeShoppingTheme {
        Surface {
            Column {
                EmailInput(
                    "",
                ) { newInput, isValid ->

                }
            }
        }
    }
}
