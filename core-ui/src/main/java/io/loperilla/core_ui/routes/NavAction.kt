package io.loperilla.core_ui.routes

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.routes
 * Created By Manuel Lopera on 6/8/24 at 20:35
 * All rights reserved 2024
 */
sealed class NavAction {
    data object PopBackStack : NavAction()
    data class Navigate(val route: Routes) : NavAction()
}