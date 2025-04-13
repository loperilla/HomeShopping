package io.loperilla.domain

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.RegisterProvider
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.LocalDataRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 22/2/25 at 10:59
 * All rights reserved 2025
 */
class DoRegisterUseCase(
    private val authRepository: AuthRepository,
    private val localDataRepository: LocalDataRepository
) {
    suspend operator fun invoke(provider: RegisterProvider): DomainResult<Unit> {
        return when (val result = authRepository.doRegister(provider)) {
            is DomainResult.Success -> {
                localDataRepository.persistUser(result.data)
                DomainResult.Success(Unit)
            }
            is DomainResult.Error -> DomainResult.Error(result.error)
        }
    }
}