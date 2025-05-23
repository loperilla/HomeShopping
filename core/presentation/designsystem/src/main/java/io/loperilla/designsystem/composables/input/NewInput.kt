package io.loperilla.designsystem.composables.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.Email
import io.loperilla.designsystem.Password
import io.loperilla.designsystem.TextSmallSize
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.text.TextSemiBold
import io.loperilla.designsystem.composables.text.TextThin
import io.loperilla.designsystem.isValidEmail
import io.loperilla.designsystem.isValidPassword
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 6/8/24 at 19:29
 * All rights reserved 2024
 */

@Composable
fun EmailInput(
    text: Email,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    labelText: String = "EMAIL",
    imeAction: ImeAction = ImeAction.Default,
    onKeyBoardNextAction: () -> Unit = {}
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
        onKeyBoardNextAction = onKeyBoardNextAction,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "emailIcon")
        }
    )
}

@Composable
fun TextInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderValue: String = "",
    labelText: String = "",
    imeAction: ImeAction = ImeAction.Default,
    onKeyBoardNextAction: () -> Unit = {},
    onKeyBoardDoneAction: () -> Unit = {}
) {
    CommonInput(
        text = text,
        labelText = labelText,
        onTextChange = onTextChange,
        placeholderText = placeholderValue,
        keyboardType = KeyboardType.Text,
        uiError = UiError("Ingresa un valor", text.isEmpty()),
        imeAction = imeAction,
        modifier = modifier,
        onKeyBoardNextAction = onKeyBoardNextAction,
        onKeyBoardDoneAction = onKeyBoardDoneAction
    )
}

@Composable
fun PasswordInput(
    text: Password,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    labelText: String = "CONTRASEÑA",
    imeAction: ImeAction = ImeAction.Default,
    onKeyBoardNextAction: () -> Unit = {},
    onKeyBoardDoneAction: () -> Unit = {}
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
        onKeyBoardNextAction = onKeyBoardNextAction,
        onKeyBoardDoneAction = onKeyBoardDoneAction,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Password, contentDescription = "passwordIcon")
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
    onKeyBoardDoneAction: () -> Unit = {},
    onKeyBoardNextAction: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var hasFocus by remember { mutableStateOf(false) }
    val showError by remember {
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
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyBoardDoneAction()
            },
            onNext = {
                onKeyBoardNextAction()
            }
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
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
            )
        ) {
            EmailInput(
                text = "correo@dominio.com",
                onTextChange = {}
            )

            PasswordInput(
                text = "1234567",
                onTextChange = {}
            )

            TextInput(
                labelText = "Nombre",
                text = "dssdfsdfsdf",
                onTextChange = {}
            )
        }
    }
}