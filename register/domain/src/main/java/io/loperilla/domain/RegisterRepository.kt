package io.loperilla.domain

import io.loperilla.domain.model.DomainResult

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain
 * Created By Manuel Lopera on 9/2/25 at 13:29
 * All rights reserved 2025
 */
interface RegisterRepository {
    suspend fun doRegister(email: String, password: String): DomainResult<Unit>
}