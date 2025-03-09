package io.loperilla.domain.usecase.auth

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.repository.AuthRepository
import io.loperilla.domain.model.repository.LocalDataRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase.auth
 * Created By Manuel Lopera on 1/3/25 at 12:18
 * All rights reserved 2025
 */
class RefreshUserUseCase(
    private val authRepository: AuthRepository,
    private val localDataRepository: LocalDataRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return when (val result = authRepository.refreshUser()){
            is DomainResult.Error -> DomainResult.Error(result.error)
            is DomainResult.Success -> {
                localDataRepository.persistUser(result.data)
                DomainResult.Success(Unit)
            }
        }
    }
}
