package io.loperilla.data

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.domain.usecase.LoginRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.data
 * Created By Manuel Lopera on 2/2/25 at 18:56
 * All rights reserved 2025
 */
class LoginRepositoryImpl(
    private val authRepository: AuthRepository
) : LoginRepository {
    override suspend fun doLogin(email: String, password: String): DomainResult<Unit>
        = authRepository.doLogin(email, password)
}