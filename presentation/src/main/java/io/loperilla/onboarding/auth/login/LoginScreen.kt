package io.loperilla.onboarding.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.NewEmailInput
import io.loperilla.core_ui.input.NewPasswordInput
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

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(MEDIUM)
    ) {
        val (image, titleLabel, loginEmail, loginPassword, loginButton, registerButton) = createRefs()
        Image(
            painter = painterResource(R.mipmap.home_shopping_logo_foreground),
            contentDescription = "Application logo",
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        TextTitle(
            text = "Inicia sesión",
            modifier = Modifier
                .constrainAs(titleLabel) {
                    top.linkTo(image.bottom, margin = MEDIUM)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        MediumSpacer()
        NewEmailInput(
            text = state.emailInputValue,
            imeAction = ImeAction.Next,
            onTextChange = { newValue ->
                onEvent(LoginEvent.EmailValueChange(newValue))
            },
            modifier = Modifier
                .constrainAs(loginEmail) {
                    top.linkTo(titleLabel.bottom, margin = LOW)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        LowSpacer()
        NewPasswordInput(
            text = state.passwordInputValue,
            imeAction = ImeAction.Done,
            onTextChange = { newValue ->
                onEvent(LoginEvent.PasswordValueChange(newValue))
            },
            modifier = Modifier
                .constrainAs(loginPassword) {
                    top.linkTo(loginEmail.bottom, margin = LOW)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        FormButton(
            textButton = stringResource(R.string.login_button_message),
            modifier = Modifier
                .constrainAs(loginButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(registerButton.top)
                },
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
            modifier = Modifier
                .constrainAs(registerButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
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
