package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.fold
import io.loperilla.domain.usecase.LoginRepository
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import io.loperilla.ui.snackbar.SnackbarAction
import io.loperilla.ui.snackbar.SnackbarController
import io.loperilla.ui.snackbar.SnackbarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 2/2/25 at 18:33
 * All rights reserved 2025
 */
class LoginViewModel(
    private val repository: LoginRepository,
    private val navigator: Navigator,
    private val snackbarController: SnackbarController
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

            is LoginEvent.LoginButtonClicked -> {
                loginButtonClicked()
            }

            LoginEvent.RegisterButtonClicked -> navigator.navigate(
                Destination.Register
            )
        }
    }

    private fun loginButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        repository.doLogin(
            email = stateFlow.value.emailInputValue,
            password = stateFlow.value.passwordInputValue
        ).fold(
            onSuccess = {
                navigateToHome()
            },
            onError = { error ->
                manageDomainError(error)
            }
        )
    }

    private fun manageDomainError(error: DomainError) = viewModelScope.launch {
        snackbarController.sendEvent(
            SnackbarEvent(
                message = error.message ?: "Hubo un error al iniciar sesión",
                actionLabel = SnackbarAction(
                    name = "Ocultar",
                    action = {}
                )
            )
        )
    }

    private fun navigateToHome() = viewModelScope.launch {
        navigator.navigate(
            Destination.Home
        )
    }
}