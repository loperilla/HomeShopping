package io.loperilla.onboarding_domain.model.auth

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.firebase.auth
 * Created By Manuel Lopera on 21/4/23 at 16:59
 * All rights reserved 2023
 */
sealed class AuthResult {
    data object AuthSuccess : AuthResult()
    data class AuthFail(val errorMessage: String) : AuthResult()
    data object AuthNone : AuthResult()
    data object LoadingRequest : AuthResult()
}
