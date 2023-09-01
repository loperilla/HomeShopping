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
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.routes.Routes
import io.loperilla.model.SplashUIState
import io.loperilla.onboarding.addshoppingCart.AddShoppingCart
import io.loperilla.onboarding.auth.loginScreen
import io.loperilla.onboarding.auth.registerScreen
import io.loperilla.onboarding.home.HomeScreen

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
            HomeShoppingTheme {
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
                // A surface container using the 'background' color from the theme
                val navController: NavHostController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (uiState == SplashUIState.NoAuthenticated)
                        Routes.AUTH.route
                    else
                        Routes.HOME.route,
                ) {
                    navigation(startDestination = Routes.LOGIN.route, route = Routes.AUTH.route) {
                        loginScreen(navController::navigate)
                        registerScreen(navController::navigate)
                    }

                    composable(Routes.HOME.route) {
                        HomeScreen(navController::navigate)
                    }

                    composable(Routes.ADD_SHOPPING.route) {
                        AddShoppingCart(
                            popBackStack = {
                                navController.popBackStack()
                            },
                            navController::navigate
                        )
                    }
                }
            }
        }
    }
}
