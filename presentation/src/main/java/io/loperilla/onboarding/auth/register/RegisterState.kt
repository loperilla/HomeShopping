package io.loperilla.onboarding.auth.register

import io.loperilla.core_ui.Email
import io.loperilla.core_ui.Password

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.register
 * Created By Manuel Lopera on 6/8/24 at 20:54
 * All rights reserved 2024
 */
data class RegisterState(
    val emailInputValue: Email = "",
    val passwordInputValue: Password = "",
    val showErrorSnackbar: Boolean = false
)
