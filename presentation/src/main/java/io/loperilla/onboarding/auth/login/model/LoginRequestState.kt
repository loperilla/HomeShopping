package io.loperilla.onboarding.auth.login.model

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.login.model
 * Created By Manuel Lopera on 23/4/23 at 11:41
 * All rights reserved 2023
 */
data class LoginRequestState(
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)
