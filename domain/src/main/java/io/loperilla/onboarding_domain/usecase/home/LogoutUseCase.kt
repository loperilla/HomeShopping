package io.loperilla.onboarding_domain.usecase.home

import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.home
 * Created By Manuel Lopera on 24/8/23 at 20:41
 * All rights reserved 2023
 */
class LogoutUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {
    suspend operator fun invoke() = repository.doLogout()
}
