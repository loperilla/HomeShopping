package io.loperilla.onboarding.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.model.auth.AuthResult
import io.loperilla.onboarding_domain.usecase.DoLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private var _passwordInputValue: MutableStateFlow<String> = MutableStateFlow("")
    val passwordInputValue: StateFlow<String> = _passwordInputValue

    private var _emailInputValue: MutableStateFlow<String> = MutableStateFlow("")
    val emailInputValue: StateFlow<String> = _emailInputValue

    private var _loginRequestState = MutableStateFlow<AuthResult>(AuthResult.AuthNone)
    val loginRequestState: StateFlow<AuthResult> = _loginRequestState
    fun emailValueChange(newEmailValue: String) {
        viewModelScope.launch {
            _emailInputValue.value = newEmailValue
        }
    }

    fun passwordValueChange(newPasswordValue: String) {
        viewModelScope.launch {
            _passwordInputValue.value = newPasswordValue
        }
    }

    fun loginButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _loginRequestState.value = AuthResult.LoadingRequest
            val result = doLoginUseCase(emailInputValue.value, passwordInputValue.value)

            _loginRequestState.value = result
        }
    }

}
