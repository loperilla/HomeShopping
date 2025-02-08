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
import io.loperilla.onboarding_presentation.R
import io.loperilla.ui.CommonTopBar
import io.loperilla.ui.Screen
import io.loperilla.ui.TransparentScaffold
import io.loperilla.ui.button.FormButton
import io.loperilla.ui.input.EmailInput
import io.loperilla.ui.input.PasswordInput
import io.loperilla.ui.isValidEmail
import io.loperilla.ui.isValidPassword
import io.loperilla.ui.previews.PIXEL_33_NIGHT
import io.loperilla.ui.text.TextTitle

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
    io.loperilla.ui.Screen {
        io.loperilla.ui.TransparentScaffold(
            modifier = modifier,
            topBar = {
                io.loperilla.ui.CommonTopBar(
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

                io.loperilla.ui.text.TextTitle(
                    text = "Registre tu cuenta",
                    textColor = Color.White,
                    modifier = Modifier
                )

                io.loperilla.ui.input.EmailInput(
                    text = state.emailInputValue,
                    imeAction = ImeAction.Next,
                    onTextChange = { newValue ->
                        onEvent(RegisterEvent.EmailValueChange(newValue))
                    },
                    modifier = Modifier
                )

                io.loperilla.ui.input.PasswordInput(
                    modifier = Modifier,
                    text = state.passwordInputValue,
                    imeAction = ImeAction.Done,
                    onTextChange = {
                        onEvent(RegisterEvent.PasswordValueChange(it))
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
                io.loperilla.ui.button.FormButton(
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

@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
fun RegisterScreenPrev() {
    io.loperilla.ui.Screen {
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