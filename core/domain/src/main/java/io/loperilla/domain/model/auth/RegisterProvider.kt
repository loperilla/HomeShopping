package io.loperilla.domain.model.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.model.auth
 * Created By Manuel Lopera on 9/3/25 at 14:02
 * All rights reserved 2025
 */
sealed class RegisterProvider {
    data object Google : RegisterProvider()
    data class MailPassword(val email: String, val password: String) : RegisterProvider()
}