package io.loperilla.onboarding.auth.register

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.register
 * Created By Manuel Lopera on 6/8/24 at 20:55
 * All rights reserved 2024
 */
sealed class RegisterEvent {
    data class EmailValueChange(val emailValue: String) : RegisterEvent()
    data class PasswordValueChange(val passwordValue: String) : RegisterEvent()
    data object DoRegister : RegisterEvent()
    data object OnBackPressed : RegisterEvent()
    data object HideSnackbar : RegisterEvent()

}
