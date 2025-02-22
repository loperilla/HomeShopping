package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.getOrNull
import io.loperilla.domain.usecase.GetCurrentUserUseCase
import io.loperilla.domain.usecase.GetLastShoppingListUseCase
import io.loperilla.domain.usecase.LogOutUseCase
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val getLastHomeShoppingListUseCase: GetLastShoppingListUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow: StateFlow<HomeState> = _stateFlow
        .onStart {
            initState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            HomeState()
        )

    fun onEvent(event: HomeEvent) = viewModelScope.launch {
        when (event) {
            HomeEvent.LogOut -> onLogOutClicked()
            HomeEvent.OnClickCreateNewShoppingList -> TODO()
        }
    }

    private fun initState() = viewModelScope.launch {
        _stateFlow.update {
            it.copy(
                isLoading = false,
                currentUser = getCurrentUserUseCase().getOrNull(),
                lastShoppingList = getLastHomeShoppingListUseCase().getOrNull()
            )
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