package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import io.loperilla.core_ui.CompraCasaTheme
import io.loperilla.core_ui.LOW
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompraCasaTheme {
                Surface {
                    // A surface container using the 'background' color from the theme
                    val navController: NavHostController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(LOW),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(navBackStackEntry?.destination?.route ?: "")
                                },
                                navigationIcon = {
                                    if (navBackStackEntry?.destination?.route != Routes.LOGIN.route) {
                                        IconButton(
                                            onClick = {
                                                navController.popBackStack()
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Go back"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Routes.AUTH.route,
                            modifier = Modifier
                                .padding(paddingValues)
                        ) {
                            navigation(startDestination = Routes.LOGIN.route, route = Routes.AUTH.route) {
                                loginScreen(navController::navigate)
                                registerScreen(navController::navigate)
                            }

                            composable(Routes.HOME.route) {
                                HomeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
