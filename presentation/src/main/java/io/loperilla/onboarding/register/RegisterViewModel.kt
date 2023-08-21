package io.loperilla.onboarding.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.model.auth.AuthResult
import io.loperilla.onboarding_domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.register
 * Created By Manuel Lopera on 20/8/23 at 17:24
 * All rights reserved 2023
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private var _passwordInputValue: MutableStateFlow<String> = MutableStateFlow("")
    val passwordInputValue: StateFlow<String> = _passwordInputValue

    private var _emailInputValue: MutableStateFlow<String> = MutableStateFlow("")
    val emailInputValue: StateFlow<String> = _emailInputValue

    private var _authState: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.AuthNone)
    val authState: StateFlow<AuthResult> = _authState

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

    fun doRegister() {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthResult.LoadingRequest

            val result = registerUseCase(emailInputValue.value, passwordInputValue.value)
            _authState.value = result
        }
    }
}
