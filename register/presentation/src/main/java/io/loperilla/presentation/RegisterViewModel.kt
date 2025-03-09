package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.DoRegisterUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.auth.RegisterProvider
import io.loperilla.domain.model.fold
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import io.loperilla.ui.snackbar.SnackbarController
import io.loperilla.ui.snackbar.SnackbarEvent
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
 * Created By Manuel Lopera on 9/2/25 at 13:37
 * All rights reserved 2025
 */
class RegisterViewModel(
    private val doRegisterUseCase: DoRegisterUseCase,
    private val navigator: Navigator,
    private val snackbarController: SnackbarController
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val stateFlow: StateFlow<RegisterState> = _stateFlow
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            RegisterState()
        )

    fun onEvent(event: RegisterEvent) = viewModelScope.launch {
        when (event) {
            is RegisterEvent.EmailValueChange -> _stateFlow.update {
                it.copy(email = event.emailValue)
            }

            is RegisterEvent.PasswordValueChange -> _stateFlow.update {
                it.copy(password = event.passwordValue)
            }

            is RegisterEvent.RepeatPasswordValueChange -> _stateFlow.update {
                it.copy(confirmPassword = event.repeatPasswordValue)
            }

            RegisterEvent.DoRegister -> doRegister()
            RegisterEvent.DoGoogleRegister -> doGoogleRegister()
            RegisterEvent.OnBackPressed -> navigator.navigateUp()
        }
    }

    private suspend fun doRegister() {
        val email = stateFlow.value.email
        val password = stateFlow.value.password

        doRegisterUseCase(RegisterProvider.MailPassword(email, password)).fold(
            onSuccess = {
                navigateToHome()
            },
            onError = { error ->
                performDomainError(error)
            }
        )
    }

    private suspend fun doGoogleRegister() {
        doRegisterUseCase(RegisterProvider.Google).fold(
            onSuccess = {
                navigateToHome()
            },
            onError = { error ->
                performDomainError(error)
            }
        )
    }

    private fun navigateToHome() = viewModelScope.launch {
        navigator.navigate(
            Destination.Home
        )
    }

    private fun performDomainError(error: DomainError) = viewModelScope.launch {
        val snackbarEvent = SnackbarEvent(
            message = error.message ?: "Unknown error",
            actionLabel = null
        )
        snackbarController.sendEvent(snackbarEvent)
    }
}
