package io.loperilla.onboarding.auth.login

import io.loperilla.core_ui.Email
import io.loperilla.core_ui.Password
import io.loperilla.core_ui.routes.Routes
import io.loperilla.onboarding_domain.model.auth.AuthResult

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.auth.login
 * Created By Manuel Lopera on 6/8/24 at 19:02
 * All rights reserved 2024
 */
data class LoginState(
    val emailInputValue: Email = "",
    val passwordInputValue: Password = "",
    val showLoadingAnimation: Boolean = false,
    val loginRequestState: AuthResult = AuthResult.AuthNone,
    val newRoute: Routes? = null
)
