package io.loperilla.domain.usecase

import io.loperilla.domain.repository.AuthRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase
 * Created By Manuel Lopera on 19/2/25 at 20:09
 * All rights reserved 2025
 */
class LogOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.doLogout()
}