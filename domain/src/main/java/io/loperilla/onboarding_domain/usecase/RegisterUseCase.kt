package io.loperilla.onboarding_domain.usecase

import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.usecase
 * Created By Manuel Lopera on 20/8/23 at 17:38
 * All rights reserved 2023
 */
class RegisterUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.doRegister(email, password)
}