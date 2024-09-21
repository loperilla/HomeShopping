package io.loperilla.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.loperilla.onboarding.auth.login.LoginScreen
import io.loperilla.onboarding.auth.login.LoginViewModel
import io.loperilla.onboarding.auth.register.RegisterScreen
import io.loperilla.onboarding.auth.register.RegisterViewModel
import io.loperilla.onboarding.commerce.CommerceScreen
import io.loperilla.onboarding.commerce.CommerceViewModel
import io.loperilla.onboarding.home.HomeScreen
import io.loperilla.onboarding.home.HomeViewModel
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.ObserveAsEvents
import io.loperilla.onboarding.navigator.routes.Destination.AuthGraph
import io.loperilla.onboarding.navigator.routes.Destination.COMMERCE
import io.loperilla.onboarding.navigator.routes.Destination.Home
import io.loperilla.onboarding.navigator.routes.Destination.Login
import io.loperilla.onboarding.navigator.routes.Destination.Register
import io.loperilla.onboarding.navigator.routes.NavigationAction

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding
 * Created By Manuel Lopera on 6/8/24 at 18:42
 * All rights reserved 2024
 */

@Composable
fun AppNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when(action) {
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
        navigation<AuthGraph>(
            startDestination = Login
        ) {
            composable<Login> {
                val viewModel: LoginViewModel = hiltViewModel()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                LoginScreen(
                    state,
                    viewModel::onEvent
                )
            }
            composable<Register> {
                val viewModel: RegisterViewModel = hiltViewModel()
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                RegisterScreen(
                    state,
                    viewModel::onEvent
                )
            }
        }
        composable<Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val state by homeViewModel.stateFlow.collectAsStateWithLifecycle()

            HomeScreen(state, homeViewModel::onEvent)
        }

        composable<COMMERCE> {
            val commerceViewModel = hiltViewModel<CommerceViewModel>()
            val state by commerceViewModel.stateFlow.collectAsStateWithLifecycle()
            CommerceScreen(state, commerceViewModel::onEvent)
        }
    }
}

//
//        navigation(
//            startDestination = Routes.SHOPPING_BASKET.SELECT_COMMERCE,
//            route = Routes.SHOPPING_BASKET
//        ) {
//            composable(Routes.SHOPPING_BASKET.SELECT_COMMERCE) {
//                val selectCommerceViewModel = hiltViewModel<SelectCommerceViewModel>()
//                val state by selectCommerceViewModel.stateFlow.collectAsStateWithLifecycle()
//                if (state.backPressed) {
//                    navController.navigate(Routes.HOME)
//                }
//
//                state.commerceSelected?.let {
//                    navController.navigate(
//                        Routes.SHOPPING_BASKET.NEW(it)
//                    )
//                }
//                SelectCommerceScreen(state, selectCommerceViewModel::onEvent)
//            }
//            composable(Routes.SHOPPING_BASKET.NEW) { navBackStackEntry ->
//                val commerce = navBackStackEntry.toRoute<Commerce>()
//                val shoppingBasketViewModel =
//                    hiltViewModel<NewShoppingBasketViewModel, NewShoppingBasketViewModelFactory> {
//                        it.create(commerce)
//                    }
//                val state by shoppingBasketViewModel.stateFlow.collectAsStateWithLifecycle()
//
//                state.newActionNav?.let {
//                    when (it) {
//                        is NavAction.Navigate -> {
//                            if (it == Routes.SHOPPING_BASKET.NEW_ITEM) {
//                                navController.navigate(
//                                    Routes.SHOPPING_BASKET.NEW_ITEM.createRoute(
//                                        id, name
//                                    )
//                                )
//                            } else {
//                                navController.navigate(it)
//                            }
//                        }
//
//                        NavAction.PopBackStack -> navController.navigate(Routes.SHOPPING_BASKET.SELECT_COMMERCE)
//                    }
//                }
//                NewShoppingBasketScreen(name, state, shoppingBasketViewModel::onEvent)
//            }
//
//            composable(Routes.SHOPPING_BASKET.NEW_ITEM) { navBackStackEntry ->
//                val id = navBackStackEntry.arguments?.getString("id") ?: ""
//                val name = navBackStackEntry.arguments?.getString("name") ?: ""
//                val commerce = Commerce(id, name)
//                val viewModel = hiltViewModel<AddProductViewModel, AddProductViewModelFactory> {
//                    it.create(commerce)
//                }
//                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
//
//                state.newRoute?.let {
//                    when (it) {
//                        is NavAction.Navigate -> navController.navigate(it)
//                        NavAction.PopBackStack -> navController.navigate(
//                            Routes.SHOPPING_BASKET.NEW.createRoute(
//                                id,
//                                name
//                            )
//                        )
//                    }
//                }
//
//                AddProductScreen(name, state, viewModel::onEvent)
//            }
//        }
//    }
//}