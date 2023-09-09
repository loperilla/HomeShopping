package io.loperilla.onboarding.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.EmailInput
import io.loperilla.core_ui.input.PasswordInput
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.spacers.LowSpacer
import io.loperilla.core_ui.spacers.MediumSpacer
import io.loperilla.onboarding_presentation.R

/*****
 * Project: CompraCasa
 * From: io.loperilla.compracasa.login
 * Created By Manuel Lopera on 21/4/23 at 17:52
 * All rights reserved 2023
 */

@Composable
fun LoginScreen(
    emailValue: String,
    passwordValue: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier,
    loginButtonClicked: () -> Unit,
    registerButtonClicked: () -> Unit
) {
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(MEDIUM)
    ) {
        val (image, loginEmail, loginPassword, loginButton, registerButton) = createRefs()
        Image(
            painter = painterResource(R.mipmap.home_shopping_logo_foreground),
            contentDescription = "Application logo",
            modifier = modifier
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        MediumSpacer()
        EmailInput(
            modifier = Modifier
                .constrainAs(loginEmail) {
                    top.linkTo(image.bottom, margin = LOW)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            inputValue = emailValue,
            onValueChange = { newValue, isInputValid ->
                onEmailChange(newValue)
                isEmailValid = isInputValid
            }
        )
        LowSpacer()
        PasswordInput(
            modifier = Modifier
                .constrainAs(loginPassword) {
                    top.linkTo(loginEmail.bottom, margin = LOW)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            inputValue = passwordValue,
            onValueChange = { newValue, isInputValid ->
                onPasswordChange(newValue)
                isPasswordValid = isInputValid
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
                loginButtonClicked()
            },
            enableButton = isEmailValid && isPasswordValid
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
                registerButtonClicked()
            }
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun LoginScreenPrev() {
    HomeShoppingTheme {
        Surface {
            LoginScreen(
                "",
                "",
                { },
                { },
                Modifier,
                { },
                { }
            )
        }
    }
}
