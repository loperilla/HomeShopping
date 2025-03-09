package io.loperilla.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.button.CustomButton
import io.loperilla.designsystem.composables.button.SimpleButton
import io.loperilla.designsystem.composables.input.EmailInput
import io.loperilla.designsystem.composables.input.PasswordInput
import io.loperilla.designsystem.composables.spacers.FullWeightSpacer
import io.loperilla.designsystem.composables.spacers.MediumSpacer
import io.loperilla.designsystem.composables.text.TextSemiBold
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.presentation.designsystem.R.mipmap


/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/2/25 at 13:37
 * All rights reserved 2025
 */

@Composable
fun RegisterScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    BackHandler {
        onEvent(RegisterEvent.OnBackPressed)
    }
    TransparentScaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                topBarText = "",
                navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navActionClick = {
                    keyboardController?.hide()
                    onEvent(RegisterEvent.OnBackPressed)
                }
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(mipmap.brand),
                contentDescription = "Application logo",
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            MediumSpacer()

            TextTitle(
                text = "Registre tu cuenta",
                textColor = Color.White,
                modifier = Modifier
            )

            MediumSpacer()

            EmailInput(
                text = state.email,
                imeAction = ImeAction.Next,
                onKeyBoardNextAction = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onTextChange = { newValue ->
                    onEvent(RegisterEvent.EmailValueChange(newValue))
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
            )

            PasswordInput(
                text = state.password,
                imeAction = ImeAction.Next,
                onKeyBoardNextAction = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onTextChange = {
                    onEvent(RegisterEvent.PasswordValueChange(it))
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
            )

            PasswordInput(
                text = state.confirmPassword,
                imeAction = ImeAction.Done,
                labelText = "Repite contraseña",
                onKeyBoardDoneAction = {
                    if (state.isFormValid) {
                        onEvent(RegisterEvent.DoRegister)
                    }
                },
                onTextChange = {
                    onEvent(RegisterEvent.RepeatPasswordValueChange(it))
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
            )
            FullWeightSpacer()
            SimpleButton(
                textButton = "Registro",
                modifier = Modifier,
                onClickButton = {
                    keyboardController?.hide()
                    onEvent(RegisterEvent.DoRegister)
                },
                enableButton = state.isFormValid
            )

            CustomButton (
                onClickButton = {
                    keyboardController?.hide()
                    onEvent(RegisterEvent.DoGoogleRegister)
                },
                contentButton = {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextSemiBold(
                        text = "Con Google",
                        textColor = Color.White
                    )
                }
            )
        }
    }
}


@PIXEL_33_NIGHT
@Composable
fun RegisterScreenPrev() {
    Screen {
        RegisterScreen(
            state = RegisterState(),
            onEvent = {}
        )
    }
}