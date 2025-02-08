package io.loperilla.domain.repository

import io.loperilla.domain.model.DomainResult

/*****
 * Project: HomeShopping`
 * From: io.loperilla.domain.repository
 * Created By Manuel Lopera on 2/2/25 at 18:43
 * All rights reserved 2025
 */
interface AuthRepository {
    suspend fun doLogin(email: String, password: String): DomainResult<Unit>
    suspend fun doRegister(email: String, password: String): DomainResult<Unit>
    suspend fun refreshUser(): DomainResult<Unit>
    suspend fun doLogout(): DomainResult<Unit>
}