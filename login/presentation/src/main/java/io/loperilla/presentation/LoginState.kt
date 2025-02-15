package io.loperilla.presentation

import io.loperilla.designsystem.Email
import io.loperilla.designsystem.Password
import io.loperilla.designsystem.isValidEmail
import io.loperilla.designsystem.isValidPassword

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 2/2/25 at 18:33
 * All rights reserved 2025
 */
data class LoginState(
    val emailInputValue: Email = "",
    val passwordInputValue: Password = "",
) {
    val isValidForm = emailInputValue.isValidEmail && passwordInputValue.isValidPassword
}
