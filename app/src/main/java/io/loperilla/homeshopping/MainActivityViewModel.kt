package io.loperilla.homeshopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding_domain.model.SplashUIState
import io.loperilla.onboarding_domain.usecase.auth.SplashUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 25/8/23 at 20:14
 * All rights reserved 2023
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    splashUseCase: SplashUseCase
) : ViewModel() {
    private var _splashUiState: MutableStateFlow<SplashUIState> = MutableStateFlow(SplashUIState.Loading)
    val splashUiState: StateFlow<SplashUIState> = _splashUiState

    init {
        viewModelScope.launch {
            splashUseCase().fold(
                onSuccess = {
                    _splashUiState.value = SplashUIState.Success
                },
                onFailure = {
                    _splashUiState.value = SplashUIState.NoAuthenticated
                }
            )
        }
    }
}
