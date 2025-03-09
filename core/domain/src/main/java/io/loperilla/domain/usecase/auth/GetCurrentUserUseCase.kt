package io.loperilla.domain.usecase.auth

import io.loperilla.domain.model.repository.LocalDataRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase
 * Created By Manuel Lopera on 22/2/25 at 09:25
 * All rights reserved 2025
 */
class GetCurrentUserUseCase(
    private val localDataRepository: LocalDataRepository
) {
    suspend operator fun invoke() = localDataRepository.getUser()
}