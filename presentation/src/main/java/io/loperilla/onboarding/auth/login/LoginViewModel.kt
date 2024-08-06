package io.loperilla.onboarding.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.Routes
import io.loperilla.onboarding_domain.usecase.auth.DoLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.compracasa.login
 * Created By Manuel Lopera on 21/4/23 at 18:02
 * All rights reserved 2023
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val stateFlow: StateFlow<LoginState> = _stateFlow.asStateFlow()

    fun onEvent(event: LoginEvent) {
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
            LoginEvent.RegisterButtonClicked -> _stateFlow.update {
                it.copy(
                    newRoute = Routes.AUTH.REGISTER
                )
            }
        }
    }

    fun loginButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
//        _loginRequestState.value = AuthResult.LoadingRequest
//        val result = doLoginUseCase(emailInputValue.value, passwordInputValue.value)
//
//        _loginRequestState.value = result
    }
}
