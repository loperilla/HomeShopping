package io.loperilla.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.fold
import io.loperilla.domain.usecase.auth.RefreshUserUseCase
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.splash.presentation
 * Created By Manuel Lopera on 9/2/25 at 12:42
 * All rights reserved 2025
 */
class WelcomeViewModel(
    private val navigator: Navigator,
    private val refreshUserUseCase: RefreshUserUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<WelcomeState> = MutableStateFlow(WelcomeState.Loading)
    val stateFlow: StateFlow<WelcomeState> = _stateFlow
        .onStart {
            viewModelScope.launch {
                refreshUserUseCase().fold(
                    onSuccess = {
                        onSuccess()
                    },
                    onError = {
                        _stateFlow.value = WelcomeState.Finish
                    }
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0),
            WelcomeState.Loading
        )

    private fun onSuccess() = viewModelScope.launch {
        _stateFlow.value = WelcomeState.Finish
        navigator.navigate(
            Destination.Home
        ) {
            popUpTo(Destination.Welcome) { inclusive = true }
        }
    }

    fun onEvent(newEvent: WelcomeEvent) = viewModelScope.launch {
        when (newEvent) {
            WelcomeEvent.NavigateToLogin -> navigator.navigate(Destination.Login)
            WelcomeEvent.NavigateToRegister -> navigator.navigate(Destination.Register)
        }
    }
    
    sealed class WelcomeState {
        data object Loading: WelcomeState()
        data object Finish: WelcomeState()
    }
}