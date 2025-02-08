package io.loperilla.onboarding.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.routes.Destination
import io.loperilla.onboarding_domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.register
 * Created By Manuel Lopera on 20/8/23 at 17:24
 * All rights reserved 2023
 */

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val navigator: Navigator
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val stateFlow: StateFlow<RegisterState> = _stateFlow.asStateFlow()

    fun onEvent(event: RegisterEvent) = viewModelScope.launch {
        when (event) {
            is RegisterEvent.EmailValueChange -> _stateFlow.update {
                it.copy(
                    emailInputValue = event.emailValue
                )
            }

            is RegisterEvent.PasswordValueChange -> _stateFlow.update {
                it.copy(
                    passwordInputValue = event.passwordValue
                )
            }

            RegisterEvent.DoRegister -> doRegister()
            RegisterEvent.OnBackPressed -> navigator.navigateUp()

            RegisterEvent.HideSnackbar -> _stateFlow.update {
                it.copy(showErrorSnackbar = false)
            }
        }
    }

    private fun doRegister() = viewModelScope.launch(Dispatchers.IO) {
        registerUseCase(
            _stateFlow.value.emailInputValue,
            _stateFlow.value.passwordInputValue
        ).fold(
            onSuccess = {
                navigator.navigate(
                    Destination.Home,
                    navOptions = {
                        popUpTo(Destination.AuthGraph) {
                            inclusive = true
                        }
                    }
                )
            },
            onFailure = {
                _stateFlow.update {
                    it.copy(showErrorSnackbar = true)
                }
            }
        )
    }
}
