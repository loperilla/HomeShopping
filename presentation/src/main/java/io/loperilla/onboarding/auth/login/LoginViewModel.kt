package io.loperilla.onboarding.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.routes.Destination
import io.loperilla.onboarding_domain.usecase.auth.DoLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: CompraCasa
 * From: io.loperilla.compracasa.login
 * Created By Manuel Lopera on 21/4/23 at 18:02
 * All rights reserved 2023
 */


class LoginViewModel(
    private val doLoginUseCase: DoLoginUseCase,
    private val navigator: Navigator
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val stateFlow: StateFlow<LoginState> = _stateFlow.asStateFlow()

    fun onEvent(event: LoginEvent) = viewModelScope.launch {
        when (event) {
            is LoginEvent.EmailValueChange -> _stateFlow.update {
                it.copy(
                    emailInputValue = event.newEmailValue
                )
            }

            is LoginEvent.PasswordValueChange -> {
                _stateFlow.update {
                    it.copy(
                        passwordInputValue = event.newPasswordValue
                    )
                }
            }

            is LoginEvent.LoginButtonClicked -> loginButtonClicked()
            LoginEvent.RegisterButtonClicked -> navigator.navigate(
                Destination.Register,
                navOptions = {
                    popUpTo(Destination.AuthGraph) {
                        inclusive = true
                    }
                }
            )

            LoginEvent.HideSnackbar -> _stateFlow.update {
                it.copy(showSnackbarError = false)
            }
        }
    }

    private fun loginButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        doLoginUseCase(
            email = stateFlow.value.emailInputValue,
            password = stateFlow.value.passwordInputValue
        ).fold(
            onSuccess = {
                navigator.navigate(
                    Destination.Home
                )
            },
            onFailure = {
                _stateFlow.update {
                    it.copy(showSnackbarError = true)
                }
            }
        )
    }
}
