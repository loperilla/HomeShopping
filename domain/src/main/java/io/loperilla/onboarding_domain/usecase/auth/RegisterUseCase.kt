package io.loperilla.onboarding_domain.usecase.auth

import io.loperilla.data.repository.auth.AuthRepository
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.usecase
 * Created By Manuel Lopera on 20/8/23 at 17:38
 * All rights reserved 2023
 */
class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.doRegister(email, password)
}