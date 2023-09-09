package io.loperilla.core_ui.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import io.loperilla.core_ui.R
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.spacers.MediumSpacer
import io.loperilla.core_ui.text.TextThin
import kotlin.reflect.KFunction1

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 6/9/23 at 20:27
 * All rights reserved 2023
 */

@Composable
fun EmailInput(
    inputValue: String,
    modifier: Modifier = Modifier,
    placeholderValue: String = "correo@dominio.com",
    onValueChange: (String, Boolean) -> Unit
) {
    CommonInput(
        inputValue = inputValue,
        placeholderValue = placeholderValue,
        errorTextDescription = stringResource(R.string.email_text_description),
        modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        validator = InputValidators.EMAIL::isValid,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "emailIcon")
        }
    )
}

@Composable
fun PasswordInput(
    inputValue: String,
    modifier: Modifier = Modifier,
    placeholderValue: String = "*********",
    onValueChange: (String, Boolean) -> Unit
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    CommonInput(
        inputValue = inputValue,
        placeholderValue = placeholderValue,
        errorTextDescription = stringResource(R.string.password_text_description),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        validator = InputValidators.PASSWORD::isValid,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
        },
        textMaxLength = 16,
        trailingIcon = {
            val endIcon = if (isPasswordVisible) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }

            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(imageVector = endIcon, contentDescription = "show or hide password")
            }
        },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}


@Composable
fun CommonInput(
    inputValue: String,
    placeholderValue: String,
    errorTextDescription: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    validator: KFunction1<String, Boolean>,
    onValueChange: (String, Boolean) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textMaxLength: Int = Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var hasBadInput by remember { mutableStateOf(false) }
    var hasFocus by remember { mutableStateOf(false) }
    val commonColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedTextColor = MaterialTheme.colorScheme.inversePrimary.copy(
            alpha = 0.7f
        ),
        errorTextColor = MaterialTheme.colorScheme.error,
        focusedContainerColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.7f
        ),
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        focusedLeadingIconColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.inversePrimary.copy(
            alpha = 0.7f
        ),
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.inversePrimary.copy(
            alpha = 0.7f
        ),
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorSupportingTextColor = MaterialTheme.colorScheme.error
    )
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                hasFocus = it.hasFocus
            },
        value = inputValue,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = hasBadInput && !hasFocus,
        placeholder = {
            Text(text = placeholderValue)
        },
        keyboardOptions = keyboardOptions,
        onValueChange = { newValue ->
            hasBadInput = !validator(newValue)
            if (newValue.length <= textMaxLength) {
                onValueChange(
                    newValue,
                    validator(newValue)
                )
            }
        },
        colors = commonColors,
        visualTransformation = visualTransformation,
        supportingText = {
            if (hasBadInput && !hasFocus) {
                TextThin(
                    errorTextDescription
                )
            }
        }
    )
}

@PIXEL_33_NIGHT
@Composable
fun EmailInputPreview() {
    Screen {
        Column {
            EmailInput(
                "Email"
            ) { newInput, isValid ->

            }
            MediumSpacer()
            PasswordInput(
                "Password"
            ) { newInput, isValid ->

            }
            MediumSpacer()
            EmailInput(
                ""
            ) { newInput, isValid ->

            }
            MediumSpacer()
            PasswordInput(
                ""
            ) { newInput, isValid ->

            }
        }
    }
}