package io.loperilla.onboarding_domain.model

/*****
 * Project: HomeShopping
 * From: io.loperilla.model
 * Created By Manuel Lopera on 25/8/23 at 20:16
 * All rights reserved 2023
 */
sealed class SplashUIState {
    data object Loading : SplashUIState()
    data object Success : SplashUIState()
    data object NoAuthenticated : SplashUIState()
}
