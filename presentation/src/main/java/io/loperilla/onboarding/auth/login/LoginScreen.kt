package io.loperilla.onboarding.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.EmailInput
import io.loperilla.core_ui.input.PasswordInput
import io.loperilla.core_ui.isValidEmail
import io.loperilla.core_ui.isValidPassword
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.spacers.LowSpacer
import io.loperilla.core_ui.spacers.MediumSpacer
import io.loperilla.core_ui.text.TextTitle
import io.loperilla.onboarding_presentation.R

/*****
 * Project: CompraCasa
 * From: io.loperilla.compracasa.login
 * Created By Manuel Lopera on 21/4/23 at 17:52
 * All rights reserved 2023
 */

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.showSnackbarError) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = "Hubo un error al registrar el usuario"
        )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> onEvent(LoginEvent.HideSnackbar)
            SnackbarResult.ActionPerformed -> {}
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(MEDIUM)
    ) {
        Image(
            painter = painterResource(R.mipmap.home_shopping_logo_foreground),
            contentDescription = "Application logo",
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        TextTitle(
            text = "Inicia sesión",
            textColor = Color.White,
            modifier = Modifier
                .padding(top = MEDIUM)
        )
        MediumSpacer()
        EmailInput(
            text = state.emailInputValue,
            imeAction = ImeAction.Next,
            onTextChange = { newValue ->
                onEvent(LoginEvent.EmailValueChange(newValue))
            },
            onKeyBoardNextAction = {
                focusManager.moveFocus(
                    FocusDirection.Down
                )
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(top = LOW)
        )
        LowSpacer()
        PasswordInput(
            text = state.passwordInputValue,
            imeAction = ImeAction.Done,
            onTextChange = { newValue ->
                onEvent(LoginEvent.PasswordValueChange(newValue))
            },
            onKeyBoardDoneAction = {
                keyboardController?.hide()
                onEvent(LoginEvent.LoginButtonClicked)
            },
            modifier = Modifier
                .padding(top = LOW)
        )
        LowSpacer(
            modifier = Modifier
                .weight(1f)
        )
        FormButton(
            textButton = stringResource(R.string.login_button_message),
            onClickButton = {
                keyboardController?.hide()
                onEvent(LoginEvent.LoginButtonClicked)
            },
            enableButton = state.emailInputValue.isValidEmail && state.passwordInputValue.isValidPassword
        )
        LowSpacer()
        FormButton(
            textButton = stringResource(R.string.login_register_button_text),
            true,
            onClickButton = {
                onEvent(LoginEvent.RegisterButtonClicked)
            }
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun LoginScreenPrev() {
    Screen {
        LoginScreen(
            state = LoginState(),
            onEvent = {}
        )
    }
}
