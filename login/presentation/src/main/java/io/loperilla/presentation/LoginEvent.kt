package io.loperilla.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 2/2/25 at 18:34
 * All rights reserved 2025
 */
sealed class LoginEvent {
    data class EmailValueChange(val newEmailValue: String) : LoginEvent()
    data class PasswordValueChange(val newPasswordValue: String) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
    data object RegisterButtonClicked : LoginEvent()
}