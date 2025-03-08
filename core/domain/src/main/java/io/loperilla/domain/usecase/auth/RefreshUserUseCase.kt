package io.loperilla.domain.usecase.auth

import io.loperilla.domain.repository.AuthRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase.auth
 * Created By Manuel Lopera on 1/3/25 at 12:18
 * All rights reserved 2025
 */
class RefreshUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.refreshUser()

}