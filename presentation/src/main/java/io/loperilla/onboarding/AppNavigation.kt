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
import androidx.navigation.toRoute
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketScreen
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketViewModel
import io.loperilla.onboarding.addshoppingCart.add.NewShoppingBasketViewModelFactory
import io.loperilla.onboarding.addshoppingCart.addProduct.AddProductScreen
import io.loperilla.onboarding.addshoppingCart.addProduct.AddProductViewModel
import io.loperilla.onboarding.addshoppingCart.addProduct.AddProductViewModelFactory
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
import io.loperilla.onboarding.navigator.CommerceType
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.ObserveAsEvents
import io.loperilla.onboarding.navigator.routes.Destination
import io.loperilla.onboarding.navigator.routes.Destination.AuthGraph
import io.loperilla.onboarding.navigator.routes.Destination.CommerceDest
import io.loperilla.onboarding.navigator.routes.Destination.Home
import io.loperilla.onboarding.navigator.routes.Destination.Login
import io.loperilla.onboarding.navigator.routes.Destination.NewBasketGraph
import io.loperilla.onboarding.navigator.routes.Destination.Register
import io.loperilla.onboarding.navigator.routes.Destination.SelectCommerce
import io.loperilla.onboarding.navigator.routes.NavigationAction
import io.loperilla.onboarding_domain.model.database.Commerce
import kotlin.reflect.typeOf

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
        when (action) {
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

        composable<CommerceDest> {
            val commerceViewModel = hiltViewModel<CommerceViewModel>()
            val state by commerceViewModel.stateFlow.collectAsStateWithLifecycle()
            CommerceScreen(state, commerceViewModel::onEvent)
        }

        navigation<NewBasketGraph>(
            startDestination = SelectCommerce,
        ) {
            composable<SelectCommerce> {
                val selectCommerceViewModel = hiltViewModel<SelectCommerceViewModel>()
                val state by selectCommerceViewModel.stateFlow.collectAsStateWithLifecycle()
                SelectCommerceScreen(state, selectCommerceViewModel::onEvent)
            }
            composable<Destination.NewBasketFromCommerce>(
                typeMap = mapOf(
                    typeOf<Commerce>() to CommerceType
                )
            ) { navBackStackEntry ->
                val commerce = navBackStackEntry.toRoute<Destination.NewBasketFromCommerce>().commerce
                val shoppingBasketViewModel =
                    hiltViewModel<NewShoppingBasketViewModel, NewShoppingBasketViewModelFactory> {
                        it.create(commerce)
                    }
                val state by shoppingBasketViewModel.stateFlow.collectAsStateWithLifecycle()
                NewShoppingBasketScreen(commerceSelectedName = commerce.name, state, shoppingBasketViewModel::onEvent)
            }

            composable<Destination.NewProduct>(
                typeMap = mapOf(
                    typeOf<Commerce>() to CommerceType
                )
            ) { navBackStackEntry ->
                val commerce = navBackStackEntry.toRoute<Destination.NewProduct>().commerce
                val viewModel = hiltViewModel<AddProductViewModel, AddProductViewModelFactory> {
                    it.create(commerce)
                }
                val state by viewModel.stateFlow.collectAsStateWithLifecycle()
                AddProductScreen(commerce.name, state, viewModel::onEvent)
            }
        }
    }
}
