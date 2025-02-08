package io.loperilla.domain.usecase

import io.loperilla.domain.model.DomainResult

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase
 * Created By Manuel Lopera on 2/2/25 at 18:55
 * All rights reserved 2025
 */
interface LoginRepository {
    suspend fun doLogin(email: String, password: String): DomainResult<Unit>
}