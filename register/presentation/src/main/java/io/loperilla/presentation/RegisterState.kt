package io.loperilla.presentation

import io.loperilla.designsystem.Email
import io.loperilla.designsystem.Password
import io.loperilla.designsystem.isValidEmail
import io.loperilla.designsystem.isValidPassword

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/2/25 at 13:37
 * All rights reserved 2025
 */
data class RegisterState(
    val email: Email = "",
    val password: Password = "",
    val confirmPassword: Password = ""
) {
    private val equalPasswords: Boolean
        get() = password == confirmPassword

    val isFormValid: Boolean
        get() = email.isValidEmail && password.isValidPassword && confirmPassword.isValidPassword && equalPasswords
}
