package io.loperilla.homeshopping

import androidx.compose.material3.SnackbarDuration
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
import io.loperilla.presentation.LoginScreen
import io.loperilla.presentation.LoginViewModel
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
    snackbarManager(snackbarController)
    navigatorManager(navigator, navController)

    NavHost(
        navController = navController,
        startDestination = navigator.startDestination,
        modifier = modifier
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

@Composable
private fun snackbarManager(snackbarController: SnackbarController) {
    val snackbarHost = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    ObserveAsEvents(flow = snackbarController.events) { event ->
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
}