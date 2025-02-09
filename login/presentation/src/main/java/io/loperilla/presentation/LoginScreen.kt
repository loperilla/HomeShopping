package io.loperilla.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.button.FormButton
import io.loperilla.designsystem.composables.input.EmailInput
import io.loperilla.designsystem.composables.input.PasswordInput
import io.loperilla.designsystem.composables.previews.PIXEL_33_NIGHT
import io.loperilla.designsystem.composables.spacers.FullWeightSpacer
import io.loperilla.designsystem.composables.spacers.MediumSpacer
import io.loperilla.presentation.designsystem.R.mipmap


/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 2/2/25 at 18:33
 * All rights reserved 2025
 */

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        MediumSpacer()
        Image(
            painter = painterResource(mipmap.home_shopping_logo_foreground),
            contentDescription = "Application logo",
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        MediumSpacer()
        EmailInput(
            state.emailInputValue,
            imeAction = ImeAction.Next,
            onTextChange = {
                onEvent(LoginEvent.EmailValueChange(it))
            },
            onKeyBoardNextAction = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .focusRequester(focusRequester)
        )
        MediumSpacer()

        PasswordInput(
            state.passwordInputValue,
            imeAction = ImeAction.Done,
            onTextChange = {
                onEvent(LoginEvent.PasswordValueChange(it))
            },
            onKeyBoardDoneAction = {
                keyboardController?.hide()
                onEvent(LoginEvent.LoginButtonClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .focusRequester(focusRequester)
        )
        FullWeightSpacer()
        FormButton(
            textButton = "Iniciar Sesión",
            enableButton = state.isValidForm,
            onClickButton = {
                onEvent(LoginEvent.LoginButtonClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@PIXEL_33_NIGHT
@Composable
private fun LoginScreenPrev() {
    Screen {
        LoginScreen(
            state = LoginState(),
            onEvent = {}
        )
    }
}