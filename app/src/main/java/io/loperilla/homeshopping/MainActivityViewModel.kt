package io.loperilla.homeshopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 25/8/23 at 20:14
 * All rights reserved 2023
 */

class MainActivityViewModel(
    splashUseCase: SplashUseCase,
    private val navigator: Navigator
) : ViewModel() {
    private var _splashUiState: MutableStateFlow<SplashUIState> = MutableStateFlow(SplashUIState.Loading)
    val splashUiState: StateFlow<SplashUIState> = _splashUiState

    init {
        viewModelScope.launch {
            if (splashUseCase().isSuccess) {
                _splashUiState.value = SplashUIState.Success
                navigator.navigate(
                    Destination.Home
                )
            } else {
                _splashUiState.value = SplashUIState.NoAuthenticated
                navigator.navigate(
                    Destination.AuthGraph
                )
            }
        }
    }
}
