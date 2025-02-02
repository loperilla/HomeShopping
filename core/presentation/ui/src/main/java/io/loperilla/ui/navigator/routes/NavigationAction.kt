package io.loperilla.ui.navigator.routes

import androidx.navigation.NavOptionsBuilder

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.navigator.routes
 * Created By Manuel Lopera on 2/2/25 at 17:21
 * All rights reserved 2025
 */
sealed interface NavigationAction {
    data object NavigateUp : NavigationAction
    data class Navigate(
        val route: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction
}