package io.loperilla.core_ui.input

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.utils
 * Created By Manuel Lopera on 22/4/23 at 19:55
 * All rights reserved 2023
 */
sealed class InputValidators {
    data object EMAIL : InputValidators() {
        fun isValid(email: String) = email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))
    }

    data object PASSWORD : InputValidators() {
        fun isValid(password: String) = password.length > 5
    }

    fun isURL(url: String): Boolean = try {
        java.net.URL(url).toURI()
        true
    } catch (e: Exception) {
        false
    }
}
