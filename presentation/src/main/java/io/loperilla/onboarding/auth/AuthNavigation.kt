package io.loperilla.onboarding.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.loperilla.core_ui.routes.Routes
import io.loperilla.model.auth.AuthResult
import io.loperilla.onboarding.auth.login.LoginScreen
import io.loperilla.onboarding.auth.login.LoginViewModel
import io.loperilla.onboarding.auth.register.RegisterScreen
import io.loperilla.onboarding.auth.register.RegisterViewModel
import io.loperilla.onboarding_presentation.R

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth
 * Created By Manuel Lopera on 21/8/23 at 18:35
 * All rights reserved 2023
 */

fun NavGraphBuilder.loginScreen(
    newDestination: (String) -> Unit
) {
    composable(Routes.LOGIN.route) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val emailValue by loginViewModel.emailInputValue.collectAsStateWithLifecycle()
        val passwordValue by loginViewModel.passwordInputValue.collectAsStateWithLifecycle()
        val requestState: AuthResult by loginViewModel.loginRequestState.collectAsStateWithLifecycle()

        when (requestState) {
            AuthResult.AuthSuccess -> newDestination(Routes.HOME.route)
            AuthResult.LoadingRequest -> {
                Column {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            else -> {
                if (requestState != AuthResult.AuthNone) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                    ) {
                        Text(stringResource(R.string.auth_fail_message))
                    }
                }
                LoginScreen(
                    emailValue,
                    passwordValue,
                    onEmailChange = loginViewModel::emailValueChange,
                    onPasswordChange = loginViewModel::passwordValueChange,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    loginButtonClicked = loginViewModel::loginButtonClicked
                ) {
                    newDestination(Routes.REGISTER.route)
                }
            }
        }
    }
}

fun NavGraphBuilder.registerScreen(newDestination: (String) -> Unit) {
    composable(Routes.REGISTER.route) {
        val registerViewModel: RegisterViewModel = hiltViewModel()
        val emailValue by registerViewModel.emailInputValue.collectAsStateWithLifecycle()
        val passwordValue by registerViewModel.passwordInputValue.collectAsStateWithLifecycle()
        val authState by registerViewModel.authState.collectAsStateWithLifecycle()

        when (authState) {
            AuthResult.AuthSuccess -> newDestination(Routes.HOME.route)
            AuthResult.LoadingRequest -> {
                Column {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            else -> {
                if (authState != AuthResult.AuthNone) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                    ) {
                        Text(stringResource(R.string.auth_fail_message))
                    }
                }
                RegisterScreen(
                    emailValue,
                    passwordValue,
                    onEmailChange = registerViewModel::emailValueChange,
                    onPasswordChange = registerViewModel::passwordValueChange,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    registerButtonClicked = registerViewModel::doRegister
                )
            }
        }
    }
}
