package io.loperilla.onboarding_domain.usecase.auth

import io.loperilla.data.repository.auth.AuthRepository
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.usecase
 * Created By Manuel Lopera on 23/4/23 at 12:17
 * All rights reserved 2023
 */
class DoLoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> = repository.doLogin(email, password)
}