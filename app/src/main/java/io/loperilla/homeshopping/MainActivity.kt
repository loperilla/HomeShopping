package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.routes.Routes
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
        super.onCreate(savedInstanceState)
        setContent {
            HomeShoppingTheme {
                // A surface container using the 'background' color from the theme
                val navController: NavHostController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.AUTH.route,
                ) {
                    navigation(startDestination = Routes.LOGIN.route, route = Routes.AUTH.route) {
                        loginScreen(navController::navigate)
                        registerScreen(navController::navigate)
                    }

                    composable(Routes.HOME.route) {
                        HomeScreen(navController::navigate)
                    }
                }
            }
        }
    }
}
