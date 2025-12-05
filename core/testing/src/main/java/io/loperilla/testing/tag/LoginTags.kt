package io.loperilla.testing.tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.tag
 * Created By Manuel Lopera on 30/11/25 at 10:25
 * All rights reserved 2025
 */

sealed class LoginTags(override val name: String) : Tag {
    data object RootScreen : LoginTags("login_root_screen")
    data object Icon : LoginTags("login_icon")
    data object EmailInput : LoginTags("login_email_input")
    data object PasswordInput : LoginTags("login_password_input")
    data object LoginButton : LoginTags("login_button")
    data object RegisterButton : LoginTags("register_button")
}
