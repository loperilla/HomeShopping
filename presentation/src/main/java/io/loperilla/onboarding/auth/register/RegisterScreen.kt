package io.loperilla.onboarding.auth.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.EmailInput
import io.loperilla.core_ui.input.PasswordInput
import io.loperilla.onboarding_presentation.R

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.register
 * Created By Manuel Lopera on 19/8/23 at 18:05
 * All rights reserved 2023
 */

@Composable
fun RegisterScreen(
    emailValue: String,
    passwordValue: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier,
    registerButtonClicked: () -> Unit
) {
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (loginEmail, loginPassword, loginButton) = createRefs()
        EmailInput(
            modifier = Modifier
                .constrainAs(loginEmail) {
                    top.linkTo(parent.top, margin = LOW)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            inputValue = emailValue,
            onValueChange = { newValue, isInputValid ->
                onEmailChange(newValue)
                isEmailValid = isInputValid
            }
        )

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
            textButton = stringResource(R.string.login_register_button_text),
            modifier = Modifier
                .constrainAs(loginButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onClickButton = {
                registerButtonClicked()
            },
            enableButton = isEmailValid && isPasswordValid
        )
    }
}
