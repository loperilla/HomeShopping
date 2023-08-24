package io.loperilla.onboarding_domain.usecase.auth

import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import io.loperilla.model.auth.AuthResult
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.usecase
 * Created By Manuel Lopera on 23/4/23 at 12:17
 * All rights reserved 2023
 */
class DoLoginUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthResult = repository.doLogin(email, password)
}