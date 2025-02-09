package io.loperilla.splash.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.splash.presentation
 * Created By Manuel Lopera on 9/2/25 at 12:43
 * All rights reserved 2025
 */
sealed interface WelcomeEvent {
    data object NavigateToLogin : WelcomeEvent
    data object NavigateToRegister : WelcomeEvent
}