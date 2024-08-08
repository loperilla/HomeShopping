package io.loperilla.onboarding.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.onboarding_domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private var _stateFlow: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val stateFlow: StateFlow<RegisterState> = _stateFlow.asStateFlow()

    fun onEvent(event: RegisterEvent) {
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
            RegisterEvent.OnBackPressed -> _stateFlow.update {
                it.copy(newRoute = NavAction.PopBackStack)
            }
        }
    }

    fun doRegister() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}
