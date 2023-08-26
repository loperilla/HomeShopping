package io.loperilla.onboarding_domain.usecase.splash

import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.splash
 * Created By Manuel Lopera on 25/8/23 at 20:31
 * All rights reserved 2023
 */
class SplashUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository
) {
    suspend operator fun invoke() = repository.checkAuth()
}