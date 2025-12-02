package io.loperilla.testing.tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.tag
 * Created By Manuel Lopera on 2/12/25 at 20:15
 * All rights reserved 2025
 */

sealed class WelcomeTags(override val name: String) : Tag {
    data object WelcomeRoot : WelcomeTags("welcome_root")
    data object Logo : WelcomeTags("welcome_logo")
    data object LoginButton : WelcomeTags("welcome_btn_login")
    data object RegisterButton : WelcomeTags("welcome_btn_register")
}
