package io.loperilla.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.core_ui.routes.Routes
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketScreen
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketViewModel
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketViewModelFactory
import io.loperilla.onboarding.addshoppingCart.select_commerce.SelectCommerceScreen
import io.loperilla.onboarding.addshoppingCart.select_commerce.SelectCommerceViewModel
import io.loperilla.onboarding.auth.login.LoginScreen
import io.loperilla.onboarding.auth.login.LoginViewModel
import io.loperilla.onboarding.auth.register.RegisterScreen
import io.loperilla.onboarding.auth.register.RegisterViewModel
import io.loperilla.onboarding.commerce.CommerceScreen
import io.loperilla.onboarding.commerce.CommerceViewModel
import io.loperilla.onboarding.home.HomeScreen
import io.loperilla.onboarding.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding
 * Created By Manuel Lopera on 6/8/24 at 18:42
 * All rights reserved 2024
 */

@OptIn(ExperimentalCoroutinesApi::class)
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
                val viewModel: LoginViewModel = hiltViewModel()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()

                state.newRoute?.let {
                    navController.navigate(it.route)
                    return@let
                }

                LoginScreen(
                    state,
                    viewModel::onEvent
                )
            }

            composable(Routes.AUTH.REGISTER.route) {
                val viewModel: RegisterViewModel = hiltViewModel()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()

                state.newRoute?.let {
                    when (it) {
                        is NavAction.Navigate -> navController.navigate(it.route.route)
                        NavAction.PopBackStack -> navController.navigate(Routes.AUTH.LOGIN.route)
                    }
                }

                RegisterScreen(
                    state,
                    viewModel::onEvent
                )
            }
        }

        composable(Routes.HOME.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val state by homeViewModel.stateFlow.collectAsStateWithLifecycle()

            state.newRoute?.let {
                when (it) {
                    is NavAction.Navigate -> navController.navigate(it.route.route)
                    NavAction.PopBackStack -> navController.navigate(Routes.AUTH.route)
                }
            }
            HomeScreen(state, homeViewModel::onEvent)
        }

        composable(Routes.COMMERCE.route) {
            val commerceViewModel = hiltViewModel<CommerceViewModel>()
            val state by commerceViewModel.stateFlow.collectAsStateWithLifecycle()
            state.newRoute?.let {
                when (it) {
                    is NavAction.Navigate -> navController.navigate(it.route.route)
                    NavAction.PopBackStack -> navController.navigate(Routes.HOME.route)
                }
            }
            CommerceScreen(state, commerceViewModel::onEvent)
        }

        navigation(
            startDestination = Routes.SHOPPING_BASKET.SELECT_COMMERCE.route,
            route = Routes.SHOPPING_BASKET.route
        ) {
            composable(Routes.SHOPPING_BASKET.SELECT_COMMERCE.route) {
                val selectCommerceViewModel = hiltViewModel<SelectCommerceViewModel>()
                val state by selectCommerceViewModel.stateFlow.collectAsStateWithLifecycle()
                if (state.backPressed) {
                    navController.navigate(Routes.HOME.route)
                }

                state.commerceSelected?.let {
                    navController.navigate(
                        Routes.SHOPPING_BASKET.NEW.createRoute(
                            it.id,
                            it.name
                        )
                    )
                }
                SelectCommerceScreen(state, selectCommerceViewModel::onEvent)
            }
            composable(Routes.SHOPPING_BASKET.NEW.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: ""
                val name = navBackStackEntry.arguments?.getString("name") ?: ""

                val shoppingBasketViewModel = hiltViewModel<NewShoppingBasketViewModel, NewShoppingBasketViewModelFactory> {
                    it.create(id)
                }
                val state by shoppingBasketViewModel.stateFlow.collectAsStateWithLifecycle()

                state.newActionNav?.let {
                    when (it) {
                        is NavAction.Navigate -> navController.navigate(it.route.route)
                        NavAction.PopBackStack -> navController.navigate(Routes.HOME.route)
                    }
                }
                NewShoppingBasketScreen(name, state, shoppingBasketViewModel::onEvent)
            }
        }
    }
}