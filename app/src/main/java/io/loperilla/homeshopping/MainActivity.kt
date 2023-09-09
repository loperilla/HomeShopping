package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.routes.Routes
import io.loperilla.model.SplashUIState
import io.loperilla.onboarding.additem.AddItemScreen
import io.loperilla.onboarding.addshoppingCart.AddShoppingCart
import io.loperilla.onboarding.auth.loginScreen
import io.loperilla.onboarding.auth.registerScreen
import io.loperilla.onboarding.home.HomeScreen
import timber.log.Timber

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            Screen {
                val viewModel: MainActivityViewModel = hiltViewModel()
                val uiState: SplashUIState by viewModel.splashUiState.collectAsStateWithLifecycle()

                splashScreen.setKeepOnScreenCondition {
                    when (uiState) {
                        SplashUIState.Loading -> true
                        SplashUIState.NoAuthenticated -> {
                            false
                        }

                        SplashUIState.Success -> {
                            false
                        }
                    }
                }
                val navController: NavHostController = rememberNavController()

                val currentBack = navController.currentBackStack.collectAsStateWithLifecycle().value
                currentBack.forEach {
                    Timber.tag("backStack").i("${it.destination.route}")
                }

                NavHost(
                    navController = navController,
                    startDestination = if (uiState == SplashUIState.NoAuthenticated)
                        Routes.AUTH.route
                    else
                        Routes.HOME.route,
                ) {
                    navigation(startDestination = Routes.AUTH.LOGIN.route, route = Routes.AUTH.route) {
                        loginScreen(navController::navigate)
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
        }
    }
}
