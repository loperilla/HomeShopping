package io.loperilla.domain.model.auth

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.model
 * Created By Manuel Lopera on 9/3/25 at 11:39
 * All rights reserved 2025
 */

sealed class Credential {
    data class GoogleCredential(val googleIdToken: String): Credential()
    data class DefaultCredential(val email: String, val password: String): Credential()
}

