package io.loperilla.data

import io.loperilla.domain.RegisterRepository
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.repository.AuthRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.data
 * Created By Manuel Lopera on 9/2/25 at 13:30
 * All rights reserved 2025
 */
class RegisterRepositoryImpl(
    private val authRepository: AuthRepository
) : RegisterRepository {
    override suspend fun doRegister(email: String, password: String): DomainResult<Unit> =
        authRepository.doRegister(email, password)
}