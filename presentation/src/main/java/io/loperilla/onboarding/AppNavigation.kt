package io.loperilla.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.core_ui.routes.Routes
import io.loperilla.onboarding.additem.AddItemScreen
import io.loperilla.onboarding.addshoppingCart.AddShoppingCart
import io.loperilla.onboarding.auth.login.loginScreen
import io.loperilla.onboarding.auth.registerScreen
import io.loperilla.onboarding.home.HomeScreen

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding
 * Created By Manuel Lopera on 6/8/24 at 18:42
 * All rights reserved 2024
 */

@Composable
fun AppNavigation(
    startDestination: Routes,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        navigation(startDestination = Routes.AUTH.LOGIN.route, route = Routes.AUTH.route) {
            composable(Routes.AUTH.LOGIN.route) {
                loginScreen { navAction ->
                    when (navAction) {
                        is NavAction.Navigate -> navController.navigate(navAction.route.route)
                        NavAction.PopBackStack -> navController.popBackStack()
                    }
                }
            }
            registerScreen(navController::navigate)
        }

        composable(Routes.HOME.route) {
            HomeScreen(navController::navigate)
        }

        navigation(
            startDestination = Routes.SHOPPING_BASKET.ADD_SHOPPING.route,
            route = Routes.SHOPPING_BASKET.route
        ) {
            composable(Routes.SHOPPING_BASKET.ADD_SHOPPING.route) {
                AddShoppingCart(
                    popBackStack = {
                        navController.navigate(Routes.HOME.route) {
                            popUpTo(Routes.HOME.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToNewItem = {
                        navController.navigate(Routes.SHOPPING_BASKET.NEW_ITEM.route)
                    }
                )
            }
            composable(
                route = Routes.SHOPPING_BASKET.NEW_ITEM.route
            ) {
                AddItemScreen {
                    navController.navigate(Routes.SHOPPING_BASKET.ADD_SHOPPING.route)
                }
            }
        }
    }
}