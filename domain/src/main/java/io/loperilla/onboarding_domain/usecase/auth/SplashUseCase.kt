package io.loperilla.onboarding_domain.usecase.auth

import io.loperilla.onboarding_domain.repository.AuthRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.auth
 * Created By Manuel Lopera on 8/8/24 at 18:21
 * All rights reserved 2024
 */
class SplashUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = authRepository.refreshUser()
}