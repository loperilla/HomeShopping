package io.loperilla.core_ui.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import io.loperilla.core_ui.LOW

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 31/8/23 at 20:17
 * All rights reserved 2023
 */

@Composable
fun UrlInput(
    inputValue: String,
    modifier: Modifier = Modifier,
    placeholderValue: String = "Introduzca una URL",
    onValueChange: (String, Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LOW),
        value = inputValue,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Link, contentDescription = "emailIcon")
        },
        placeholder = {
            Text(text = placeholderValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            onValueChange(
                it,
                InputValidators.isURL(it)
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

@Composable
fun TextInput(
    inputValue: String,
    modifier: Modifier = Modifier,
    placeholderValue: String = "Introduzca texto",
    onValueChange: (String, Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LOW),
        value = inputValue,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Link, contentDescription = "emailIcon")
        },
        placeholder = {
            Text(text = placeholderValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            onValueChange(
                it,
                it.isNotEmpty()
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
