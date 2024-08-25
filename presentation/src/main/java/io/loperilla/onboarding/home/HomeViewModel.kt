package io.loperilla.onboarding.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.core_ui.routes.Routes
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
                        _stateFlow.update {
                            it.copy(
                                newRoute = NavAction.PopBackStack
                            )
                        }
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

            HomeEvent.CreateShoppingBasket -> {
                _stateFlow.update {
                    it.copy(
                        newRoute = NavAction.Navigate(Routes.SHOPPING_BASKET)
                    )
                }
            }
            is HomeEvent.ItemSelected -> {
                // TODO
            }

            HomeEvent.ChangeChipVisibility -> _stateFlow.update {
                it.copy(
                    commerceListIsVisible = !it.commerceListIsVisible
                )
            }

            HomeEvent.NavigateToCommerce -> _stateFlow.update {
                it.copy(
                    newRoute = NavAction.Navigate(Routes.COMMERCE)
                )
            }
        }
    }

}
