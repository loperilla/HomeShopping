package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.model.fold
import io.loperilla.domain.usecase.auth.GetCurrentUserUseCase
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarAction
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
 * Created By Manuel Lopera on 9/3/25 at 17:35
 * All rights reserved 2025
 */
class UserDetailViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val navigator: Navigator,
    private val snackbarController: SnackbarController
): ViewModel() {
    private var _stateFlow: MutableStateFlow<UserDetailState> = MutableStateFlow(UserDetailState())
    val stateFlow: StateFlow<UserDetailState> = _stateFlow
        .onStart {
            loadCurrentUser()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UserDetailState()
        )

    fun onEvent(event: UserDetailEvent) = viewModelScope.launch {
        when (event) {
            is UserDetailEvent.OnNameChanged -> TODO()
            UserDetailEvent.OnBackPressed -> onBackPressed()
        }
    }

    private fun loadCurrentUser() = viewModelScope.launch {
        getCurrentUserUseCase.invoke().fold(
            onSuccess = { currentUser ->
                _stateFlow.update {
                    it.copy(
                        user = currentUser
                    )
                }
            },
            onError = {
                launch {
                    snackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Hubo un error al iniciar sesión",
                            actionLabel = SnackbarAction(
                                name = "Volver",
                                action = ::onBackPressed
                            )
                        )
                    )
                }
            }
        )
    }

    private fun onBackPressed() = viewModelScope.launch {
        navigator.navigateUp()
    }
}
