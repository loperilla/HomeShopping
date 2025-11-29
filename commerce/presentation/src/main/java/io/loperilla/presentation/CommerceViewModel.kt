package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.NewCommerceUseCase
import io.loperilla.domain.RemoveCommerceUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.fold
import io.loperilla.domain.usecase.commerce.GetCommercesUseCase
import io.loperilla.ui.navigator.Navigator
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
 * Created By Manuel Lopera on 16/3/25 at 12:38
 * All rights reserved 2025
 */
class CommerceViewModel(
    private val navigator: Navigator,
    private val getCommercesUseCase: GetCommercesUseCase,
    private val newCommerceUseCase: NewCommerceUseCase,
    private val deleteCommerceUseCase: RemoveCommerceUseCase,
    private val snackbarController: SnackbarController
): ViewModel() {
    private var _stateFlow: MutableStateFlow<CommerceState> = MutableStateFlow(CommerceState())
    val stateFlow: StateFlow<CommerceState> = _stateFlow
        .onStart {
            getCommerceList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CommerceState()
        )

    private fun getCommerceList() = viewModelScope.launch {
        getCommercesUseCase().fold(
            onSuccess = { commerceList ->
                _stateFlow.update {
                    it.copy(
                        commerceList = commerceList,
                        isLoading = false
                    )
                }
            },
            onError = {
                _stateFlow.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        )
    }

    fun onEvent(newEvent: CommerceEvent) = viewModelScope.launch {
        when(newEvent) {
            CommerceEvent.GoBack -> navigator.navigateUp()
            CommerceEvent.AddNewCommerce -> _stateFlow.update {
                it.copy(
                    showNewCommerceInput = true
                )
            }
            is CommerceEvent.RemoveCommerce -> {
                _stateFlow.update {
                    it.copy(
                        showNewCommerceInput = false,
                    )
                }

                deleteCommerceUseCase(newEvent.id).fold(
                    onSuccess = {
                        getCommerceList()
                    },
                    onError = {
                        _stateFlow.update { state ->
                            state.copy(
                                isLoading = false
                            )
                        }
                        showSnackBarError(it)
                    }
                )
            }
            is CommerceEvent.NewCommerceNameChanged -> _stateFlow.update {
                it.copy(
                    newCommerceName = newEvent.name
                )
            }
            CommerceEvent.SendNewCommerce -> {
                _stateFlow.update {
                    it.copy(
                        showNewCommerceInput = false,
                        isLoading = true
                    )
                }

                newCommerceUseCase(stateFlow.value.newCommerceName).fold(
                    onSuccess = {
                        getCommerceList()
                    },
                    onError = {
                        _stateFlow.update { state ->
                            state.copy(
                                isLoading = false
                            )
                        }
                        showSnackBarError(it)
                    }
                )
            }
        }
    }

    private fun showSnackBarError(error: DomainError) = viewModelScope.launch {
        snackbarController.sendEvent(
            SnackbarEvent(
                error.message ?: "Error desconocido"
            )
        )
    }
}