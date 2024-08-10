package io.loperilla.core_ui.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.Email
import io.loperilla.core_ui.Password
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.isValidEmail
import io.loperilla.core_ui.isValidPassword
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.core_ui.text.TextThin

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 6/8/24 at 19:29
 * All rights reserved 2024
 */

@Composable
fun NewEmailInput(
    text: Email,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    labelText: String = "EMAIL",
    imeAction: ImeAction = ImeAction.Default
) {
    CommonInput(
        text = text,
        labelText = labelText,
        onTextChange = onTextChange,
        placeholderText = placeholderText,
        keyboardType = KeyboardType.Email,
        uiError = UiError("Ingresa un formato de email válido", !text.isValidEmail),
        imeAction = imeAction,
        modifier = modifier,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "emailIcon")
        }
    )
}

@Composable
fun NewPasswordInput(
    text: Password,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    labelText: String = "CONTRASEÑA",
    imeAction: ImeAction = ImeAction.Default
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    CommonInput(
        text = text,
        labelText = labelText,
        onTextChange = onTextChange,
        placeholderText = placeholderText,
        keyboardType = KeyboardType.Password,
        uiError = UiError("Ingresa una contraseña segura", !text.isValidPassword),
        imeAction = imeAction,
        modifier = modifier,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "passwordIcon")
        },
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
        },
    )
}

@Composable
private fun CommonInput(
    text: String,
    labelText: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    uiError: UiError = UiError(),
    placeholderText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var hasFocus by remember { mutableStateOf(false) }
    var showError by remember {
        mutableStateOf(if (text.isEmpty()) false else uiError.isVisible && !hasFocus)
    }
    val commonColors = TextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White.copy(
            alpha = 0.7f
        ),
        errorTextColor = MaterialTheme.colorScheme.error,
        focusedContainerColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.7f
        ),
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        focusedLeadingIconColor = Color.White,
        unfocusedLeadingIconColor = Color.White,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.White,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
    )


    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                hasFocus = it.hasFocus
            },
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = showError,
        label = {
            TextSemiBold(
                labelText,
                textSize = TextSmallSize
            )
        },
        placeholder = {
            TextThin(text = placeholderText)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        colors = commonColors,
        visualTransformation = visualTransformation,
        supportingText = {
            if (showError) {
                TextThin(
                    uiError.message,
                    textColor = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@PIXEL_33_NIGHT
@Composable
private fun NewInputPreview() {
    Screen {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterVertically
            )
        ) {
            NewEmailInput(
                text = "correo@dominio.com",
                onTextChange = {}
            )

            NewPasswordInput(
                text = "1234567",
                onTextChange = {}
            )
        }
    }
}