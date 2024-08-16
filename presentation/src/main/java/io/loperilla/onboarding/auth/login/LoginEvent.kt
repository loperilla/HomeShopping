package io.loperilla.onboarding.auth.login

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.login
 * Created By Manuel Lopera on 6/8/24 at 19:17
 * All rights reserved 2024
 */
sealed class LoginEvent {
    data class EmailValueChange(val newEmailValue: String) : LoginEvent()
    data class PasswordValueChange(val newPasswordValue: String) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
    data object RegisterButtonClicked : LoginEvent()
    data object HideSnackbar : LoginEvent()
}