package io.loperilla.homeshopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.isSuccess
import io.loperilla.domain.usecase.auth.RefreshUserUseCase
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
 * From: io.loperilla.homeshopping.presentation
 * Created By Manuel Lopera on 29/11/25 at 10:27
 * All rights reserved 2025
 */
class MainActivityViewModel(
    private val navigator: Navigator,
    private val refreshUserUseCase: RefreshUserUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState.Loading)
    val stateFlow: StateFlow<MainActivityUiState> = _stateFlow
        .onStart {
            checkIfUserIsLogged()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MainActivityUiState.Loading
        )

    private fun checkIfUserIsLogged() = viewModelScope.launch {
        val useCaseResult = refreshUserUseCase.invoke()
        if (useCaseResult.isSuccess) {
            navigator.navigateTo(Destination.Home)
        }
        _stateFlow.update {
            MainActivityUiState.Success(
                goToWelcome = useCaseResult.isSuccess
            )
        }
    }

}