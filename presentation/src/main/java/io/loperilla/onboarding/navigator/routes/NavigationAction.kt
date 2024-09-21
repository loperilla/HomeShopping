package io.loperilla.onboarding.navigator.routes

import androidx.navigation.NavOptionsBuilder

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.routes
 * Created By Manuel Lopera on 6/8/24 at 20:35
 * All rights reserved 2024
 */
sealed interface NavigationAction {
    data object NavigateUp : NavigationAction
    data class Navigate(
        val route: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction
}