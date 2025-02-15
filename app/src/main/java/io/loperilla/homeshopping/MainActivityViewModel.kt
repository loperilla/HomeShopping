package io.loperilla.homeshopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.fold
import io.loperilla.domain.repository.AuthRepository
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 15/2/25 at 10:03
 * All rights reserved 2025
 */
class MainActivityViewModel(
    private val navigator: Navigator,
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<SplashState> = MutableStateFlow(SplashState.Loading)
    val stateFlow: StateFlow<SplashState> = _stateFlow
        .onStart {
            authRepository.refreshUser().fold(
                onSuccess = {
                    onSuccessRefresh()
                },
                onError = {
                    onErrorRefresh()
                }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SplashState.Loading
        )

    private fun onErrorRefresh() = viewModelScope.launch {
        navigator.navigate(Destination.Welcome)
        _stateFlow.update {
            SplashState.NavigateToRegister
        }
    }

    private fun onSuccessRefresh() = viewModelScope.launch {
        navigator.navigate(Destination.Home)
        _stateFlow.update {
            SplashState.NavigateToLogin
        }
    }
}


sealed interface SplashState {
    data object Loading : SplashState
    data object NavigateToLogin : SplashState
    data object NavigateToRegister : SplashState
}