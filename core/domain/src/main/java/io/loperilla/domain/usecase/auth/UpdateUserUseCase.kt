package io.loperilla.domain.usecase.auth

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.UserUpdateRequest
import io.loperilla.domain.model.getOrNull
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.repository.LocalDataRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase.auth
 * Created By Manuel Lopera on 14/3/25 at 16:29
 * All rights reserved 2025
 */
class UpdateUserUseCase(
    private val authRepository: AuthRepository,
    private val localDataRepository: LocalDataRepository
) {
    suspend operator fun invoke(newUser: UserUpdateRequest): DomainResult<Unit> {
        return when (val result = authRepository.updateUser(user = newUser)) {
            is DomainResult.Error -> DomainResult.Error(result.error)
            is DomainResult.Success -> {
                localDataRepository.getUser().getOrNull()?.let { currentUser ->
                    localDataRepository.persistUser(currentUser.copy(
                        name = newUser.displayName
                    ))
                }
                DomainResult.Success(Unit)
            }
        }
    }
}