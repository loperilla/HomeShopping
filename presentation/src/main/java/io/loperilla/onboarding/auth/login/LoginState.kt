package io.loperilla.onboarding.auth.login

import io.loperilla.ui.Email
import io.loperilla.ui.Password

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.login
 * Created By Manuel Lopera on 6/8/24 at 19:02
 * All rights reserved 2024
 */
data class LoginState(
    val emailInputValue: io.loperilla.ui.Email = "",
    val passwordInputValue: io.loperilla.ui.Password = "",
    val showSnackbarError: Boolean = false,
)
