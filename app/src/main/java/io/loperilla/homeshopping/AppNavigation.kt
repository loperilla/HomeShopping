package io.loperilla.homeshopping

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.ObserveAsEvents
import io.loperilla.ui.navigator.routes.NavigationAction

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 2/2/25 at 19:15
 * All rights reserved 2025
 */

@Composable
fun AppNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    ObserveAsEvents(flow = navigator.navigationActions) { action: NavigationAction ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.route
            ) {
                action.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {

    }
}