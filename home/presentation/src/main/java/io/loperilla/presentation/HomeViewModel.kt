package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.usecase.LogOutUseCase
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 19/2/25 at 19:58
 * All rights reserved 2025
 */
class HomeViewModel(
    private val navigator: Navigator,
    private val logOutUseCase: LogOutUseCase,
    //private val getLastHomeShoppingListUseCase: GetLastHomeShoppingListUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow: StateFlow<HomeState> = _stateFlow
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            HomeState()
        )

    fun onEvent(event: HomeEvent) = viewModelScope.launch {
        when (event) {
            HomeEvent.LogOut -> onLogOutClicked()

        }
    }

    private fun onLogOutClicked() = viewModelScope.launch(Dispatchers.IO) {
        logOutUseCase().also {
            navigator.navigate(
                Destination.Login,
                navOptions = {
                    popUpTo(Destination.Login) {
                        inclusive = true
                    }
                }
            )
        }
    }
}