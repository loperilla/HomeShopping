package io.loperilla.onboarding.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.routes.Destination
import io.loperilla.onboarding_domain.usecase.auth.LogoutUseCase
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 24/8/23 at 20:19
 * All rights reserved 2023
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val commerceListUseCase: GetCommerceListUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val navigator: Navigator
//    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private var _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow: StateFlow<HomeState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            commerceListUseCase().collectLatest { commerceList ->
                _stateFlow.update {
                    it.copy(
                        commerceList = commerceList
                    )
                }
            }
        }
    }

    fun onEvent(newEvent: HomeEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (newEvent) {
            is HomeEvent.LogOut -> {
                logoutUseCase().getOrDefault(
                    _stateFlow.update {
                        it.copy(
                            showAreYouSureLogout = false
                        )
                    }.run {
                        navigator.navigate(
                            Destination.AuthGraph,
                            navOptions = {
                                popUpTo(Destination.Home) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                )
            }


            HomeEvent.HideLogoutDialog -> _stateFlow.update {
                it.copy(
                    showAreYouSureLogout = false
                )
            }

            HomeEvent.ShowLogoutDialog -> _stateFlow.update {
                it.copy(
                    showAreYouSureLogout = true
                )
            }

            HomeEvent.CreateShoppingBasket -> navigator.navigate(
                Destination.NewBasketGraph
            )

            is HomeEvent.ItemSelected -> {
                // TODO
            }

            HomeEvent.ChangeChipVisibility -> _stateFlow.update {
                it.copy(
                    commerceListIsVisible = !it.commerceListIsVisible
                )
            }

            HomeEvent.NavigateToCommerce -> navigator.navigate(
                Destination.CommerceDest
            )
        }
    }
}
