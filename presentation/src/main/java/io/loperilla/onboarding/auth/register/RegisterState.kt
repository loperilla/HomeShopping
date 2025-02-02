package io.loperilla.onboarding.auth.register

import io.loperilla.ui.Email
import io.loperilla.ui.Password

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.register
 * Created By Manuel Lopera on 6/8/24 at 20:54
 * All rights reserved 2024
 */
data class RegisterState(
    val emailInputValue: io.loperilla.ui.Email = "",
    val passwordInputValue: io.loperilla.ui.Password = "",
    val showErrorSnackbar: Boolean = false
)
