package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import io.loperilla.domain.usecase.LoginRepository
import io.loperilla.ui.navigator.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 2/2/25 at 18:33
 * All rights reserved 2025
 */
class LoginViewModel(
    private val repository: LoginRepository,
    private val navigator: Navigator
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val stateFlow: StateFlow<LoginState> = _stateFlow.asStateFlow()
//
//    fun onEvent(event: LoginEvent) = viewModelScope.launch {
//        when (event) {
//            is LoginEvent.EmailValueChange -> _stateFlow.update {
//                it.copy(
//                    emailInputValue = event.newEmailValue
//                )
//            }
//
//            is LoginEvent.PasswordValueChange -> {
//                _stateFlow.update {
//                    it.copy(
//                        passwordInputValue = event.newPasswordValue
//                    )
//                }
//            }
//
//            is LoginEvent.LoginButtonClicked -> loginButtonClicked()
//            LoginEvent.RegisterButtonClicked -> navigator.navigate(
//                Destination.Register,
//                navOptions = {
//                    popUpTo(Destination.AuthGraph) {
//                        inclusive = true
//                    }
//                }
//            )
//
//            LoginEvent.HideSnackbar -> _stateFlow.update {
//                it.copy(showSnackbarError = false)
//            }
//        }
//    }
//
//    private fun loginButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
//        repository.doLogin(
//            email = stateFlow.value.emailInputValue,
//            password = stateFlow.value.passwordInputValue
//        ).fold(
//            onSuccess = {
//                navigator.navigate(
//                    Destination.Home
//                )
//            },
//            onError = {
//                _stateFlow.update {
//                    it.copy(showSnackbarError = true)
//                }
//            }
//        )
//    }
}