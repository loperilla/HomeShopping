package io.loperilla.testing.tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.tag
 * Created By Manuel Lopera on 30/11/25 at 13:57
 * All rights reserved 2025
 */

sealed class RegisterTag(override val name: String): Tag {
    data object RegisterRootTag: RegisterTag(REGISTER_ROOT_TAG)
    data object RegisterTopBar: RegisterTag(REGISTER_TOPBAR)
    data object RegisterScreen: RegisterTag(REGISTER_SCREEN)
    data object RegisterIcon: RegisterTag(REGISTER_ICON)
    data object RegisterEmailInput: RegisterTag(REGISTER_EMAIL_INPUT)
    data object RegisterPasswordInput: RegisterTag(REGISTER_PASSWORD_INPUT)
    data object RegisterRepeatPasswordInput: RegisterTag(REGISTER_REPEAT_PASSWORD_INPUT)
    data object RegisterButton: RegisterTag(REGISTER_BUTTON)
    data object RegisterGoogleButton: RegisterTag(REGISTER_GOOGLE_BUTTON)
}
private const val REGISTER_ROOT_TAG = "register"
private const val REGISTER_TOPBAR = "register_topbar"
private const val REGISTER_SCREEN = "register_screen"
private const val REGISTER_ICON = "register_icon"
private const val REGISTER_EMAIL_INPUT = "register_email_input"
private const val REGISTER_PASSWORD_INPUT = "register_password_input"
private const val REGISTER_REPEAT_PASSWORD_INPUT = "register_repeat_password_input"
private const val REGISTER_BUTTON = "register_button"
private const val REGISTER_GOOGLE_BUTTON = "register_google_button"