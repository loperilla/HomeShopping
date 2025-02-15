package io.loperilla.presentation

import io.loperilla.designsystem.Email
import io.loperilla.designsystem.Password

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/2/25 at 13:37
 * All rights reserved 2025
 */
sealed interface RegisterEvent {
    data class EmailValueChange(val emailValue: Email) : RegisterEvent
    data class PasswordValueChange(val passwordValue: Password) : RegisterEvent
    data class RepeatPasswordValueChange(val repeatPasswordValue: Password) : RegisterEvent
    data object DoRegister : RegisterEvent
    data object OnBackPressed : RegisterEvent
}