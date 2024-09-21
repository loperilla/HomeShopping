package io.loperilla.onboarding_domain.usecase.auth

import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.onboarding_domain.usecase.query.QueryModel
import javax.inject.Inject
/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.home
 * Created By Manuel Lopera on 24/8/23 at 20:41
 * All rights reserved 2023
 */
class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val queryModel: QueryModel
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.doLogout()
            queryModel.deleteAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
