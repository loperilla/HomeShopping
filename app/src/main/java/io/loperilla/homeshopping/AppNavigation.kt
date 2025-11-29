package io.loperilla.homeshopping

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.presentation.AddProductScreen
import io.loperilla.presentation.AddProductViewModel
import io.loperilla.presentation.CommerceScreen
import io.loperilla.presentation.CommerceViewModel
import io.loperilla.presentation.HomeScreen
import io.loperilla.presentation.HomeViewModel
import io.loperilla.presentation.LoginScreen
import io.loperilla.presentation.LoginViewModel
import io.loperilla.presentation.ProductsScreen
import io.loperilla.presentation.ProductsViewModel
import io.loperilla.presentation.RegisterScreen
import io.loperilla.presentation.RegisterViewModel
import io.loperilla.presentation.UserDetailScreen
import io.loperilla.presentation.UserDetailViewModel
import io.loperilla.splash.presentation.WelcomeScreen
import io.loperilla.splash.presentation.WelcomeViewModel
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.ObserveAsEvents
import io.loperilla.ui.navigator.routes.Destination
import io.loperilla.ui.navigator.routes.NavigationAction
import io.loperilla.ui.snackbar.SnackbarController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 2/2/25 at 19:15
 * All rights reserved 2025
 */

@Composable
fun AppNavigation(
    navigator: Navigator,
    snackbarController: SnackbarController,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    navigatorManager(navigator, navController)
    val snackbarHost = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    ObserveAsEvents(flow = snackbarController.events, snackbarHost) { event ->
        coroutine.launch {
            snackbarHost.currentSnackbarData?.dismiss()
            val result = snackbarHost.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.actionLabel?.action?.invoke()
            }
        }
    }
    TransparentScaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHost
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = navigator.startDestination,
            modifier = Modifier
                .padding(it)
        ) {
            navigation<Destination.AuthGraph>(
                startDestination = Destination.Welcome,
            ) {
                composable<Destination.Welcome> {
                    val viewModel = koinViewModel<WelcomeViewModel>()
                    WelcomeScreen(
                        onEvent = viewModel::onEvent
                    )
                }
                composable<Destination.Login> {
                    val viewModel = koinViewModel<LoginViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    LoginScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                composable<Destination.Register> {
                    val viewModel = koinViewModel<RegisterViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    RegisterScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }

            composable<Destination.Home> {
                val viewModel = koinViewModel<HomeViewModel>()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<Destination.UserDetail> {
                val viewModel = koinViewModel<UserDetailViewModel>()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                UserDetailScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<Destination.Commerce> {
                val viewModel = koinViewModel<CommerceViewModel>()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                CommerceScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<Destination.Products> {
                val viewModel = koinViewModel<ProductsViewModel>()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                ProductsScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable<Destination.AddProducts> {
                val viewModel = koinViewModel<AddProductViewModel>()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                AddProductScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@Composable
private fun navigatorManager(
    navigator: Navigator,
    navController: NavHostController
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
}
