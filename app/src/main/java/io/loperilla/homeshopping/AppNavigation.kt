package io.loperilla.homeshopping

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
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
    isUserLogged: Boolean,
    modifier: Modifier = Modifier,
) {
    val startDestination = if (isUserLogged) Destination.Home else Destination.AuthGraph

    val backStack = rememberNavBackStack(startDestination)
    navigatorManager(navigator, backStack)
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
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.back() },
            entryProvider = entryProvider {
                entry<Destination.AuthGraph> {
                    // AuthGraph no tiene UI, delega en sus hijos.
                    // Para un efecto similar a navigation-compose, navega al startDestination.
                    if (backStack.size == 1 && backStack.last() == Destination.AuthGraph) {
                        backStack.navigateTo(Destination.Welcome)
                    }
                }
                entry<Destination.Welcome> {
                    val viewModel = koinViewModel<WelcomeViewModel>()
                    WelcomeScreen(
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.Login> {
                    val viewModel = koinViewModel<LoginViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    LoginScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.Register> {
                    val viewModel = koinViewModel<RegisterViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    RegisterScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.Home> {
                    val viewModel = koinViewModel<HomeViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    HomeScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.UserDetail> {
                    val viewModel = koinViewModel<UserDetailViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    UserDetailScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.Commerce> {
                    val viewModel = koinViewModel<CommerceViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    CommerceScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.Products> {
                    val viewModel = koinViewModel<ProductsViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    ProductsScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                entry<Destination.AddProducts> {
                    val viewModel = koinViewModel<AddProductViewModel>()
                    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                    AddProductScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(250)
                )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(250)
                )
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(250)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(250)
                )
            }
        )
    }
}

@Composable
private fun navigatorManager(
    navigator: Navigator,
    backStack: NavBackStack<NavKey>
) {
    ObserveAsEvents(flow = navigator.navigationActions) { action: NavigationAction ->
        when (action) {
            is NavigationAction.Navigate -> backStack.navigateTo(action.route)
            NavigationAction.NavigateUp -> backStack.back()
            is NavigationAction.NavigateUpTo -> backStack.backTo(action.route)
            is NavigationAction.NavigateAndClearStack -> backStack.set(action.destination)
        }
    }
}

fun NavBackStack<NavKey>.navigateTo(screen: NavKey) {
    add(screen)
}

fun NavBackStack<NavKey>.back() {
    if (size <= 1) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.set(screen: NavKey) {
    clear()
    add(screen)
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if (isEmpty()) return
    if (targetScreen !in this) return

    while (isNotEmpty() && last() != targetScreen) {
        removeLastOrNull()
    }

}
