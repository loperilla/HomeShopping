package io.loperilla.onboarding.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.EmailInput
import io.loperilla.core_ui.input.PasswordInput
import io.loperilla.core_ui.isValidEmail
import io.loperilla.core_ui.isValidPassword
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextTitle
import io.loperilla.onboarding_presentation.R

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.register
 * Created By Manuel Lopera on 19/8/23 at 18:05
 * All rights reserved 2023
 */
@Composable
fun RegisterScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.showErrorSnackbar) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = "Hubo un error al registrar el usuario"
        )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> onEvent(RegisterEvent.HideSnackbar)
            SnackbarResult.ActionPerformed -> {}
        }

    }
    BackHandler {
        onEvent(RegisterEvent.OnBackPressed)
    }
    Screen {
        TransparentScaffold(
            modifier = modifier,
            topBar = {
                CommonTopBar(
                    topBarText = "",
                    navActionClick = {
                        keyboardController?.hide()
                        onEvent(RegisterEvent.OnBackPressed)
                    }
                )
            }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp
                ),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(R.mipmap.home_shopping_logo_foreground),
                    contentDescription = "Application logo",
                    modifier = modifier
                        .clip(CircleShape)
                )

                TextTitle(
                    text = "Registre tu cuenta",
                    textColor = Color.White,
                    modifier = Modifier
                )

                EmailInput(
                    text = state.emailInputValue,
                    imeAction = ImeAction.Next,
                    onTextChange = { newValue ->
                        onEvent(RegisterEvent.EmailValueChange(newValue))
                    },
                    modifier = Modifier
                )

                PasswordInput(
                    modifier = Modifier,
                    text = state.passwordInputValue,
                    imeAction = ImeAction.Done,
                    onTextChange = {
                        onEvent(RegisterEvent.PasswordValueChange(it))
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
                FormButton(
                    textButton = stringResource(R.string.login_register_button_text),
                    modifier = Modifier,
                    onClickButton = {
                        keyboardController?.hide()
                        onEvent(RegisterEvent.DoRegister)
                    },
                    enableButton = state.emailInputValue.isValidEmail && state.passwordInputValue.isValidPassword
                )
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
fun RegisterScreenPrev() {
    Screen {
        RegisterScreen(
            state = RegisterState(
                emailInputValue = "correo@dominio.es",
                passwordInputValue = "123456",
                showErrorSnackbar = false
            ),
            onEvent = {}
        )
    }
}