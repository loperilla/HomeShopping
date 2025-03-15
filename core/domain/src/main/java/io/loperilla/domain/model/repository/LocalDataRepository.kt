package io.loperilla.domain.model.repository

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.auth.User

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.repository
 * Created By Manuel Lopera on 22/2/25 at 10:11
 * All rights reserved 2025
 */
interface LocalDataRepository {
    suspend fun persistUser(user: User): DomainResult<Unit>
    suspend fun getUser(): DomainResult<User>
    suspend fun clearUser(): DomainResult<Unit>
}