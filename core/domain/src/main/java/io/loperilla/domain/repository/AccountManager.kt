package io.loperilla.domain.repository

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.Credential

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.local.account
 * Created By Manuel Lopera on 9/3/25 at 10:57
 * All rights reserved 2025
 */
interface AccountManager {
    suspend fun signInWithGoogle(): DomainResult<Credential.GoogleCredential>
    suspend fun signIn(): DomainResult<Credential.DefaultCredential>
    suspend fun signUp(email: String, password: String): DomainResult<Unit>
    suspend fun clearCredential(): DomainResult<Unit>
}