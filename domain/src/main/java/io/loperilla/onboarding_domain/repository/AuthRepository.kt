package io.loperilla.onboarding_domain.repository


/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.repository
 * Created By Manuel Lopera on 8/8/24 at 16:38
 * All rights reserved 2024
 */
interface AuthRepository {
    suspend fun doLogin(email: String, password: String): Result<Unit>
    suspend fun doRegister(email: String, password: String): Result<Unit>
    suspend fun refreshUser(): Result<Unit>
    suspend fun doLogout(): Result<Unit>
}
