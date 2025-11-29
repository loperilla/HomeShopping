package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.AddProductUseCase
import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.fold
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

class AddProductViewModel(
    private val navigator: Navigator,
    private val snackBarController: SnackbarController,
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    val stateFlow: StateFlow<AddProductState> = _stateFlow
        .onStart {
            validateCurrentFormState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AddProductState()
        )

    fun onEvent(event: AddProductEvent) = viewModelScope.launch {
        when (event) {
            AddProductEvent.AddNewProduct -> {
                _stateFlow.update {
                    it.copy(
                        isLoading = true
                    )
                }
                addProductUseCase.invoke(_stateFlow.value.newProductName).fold(
                    onSuccess = {
                        goBack()
                    },
                    onError = {
                        showError(it)
                    }
                )
            }

            AddProductEvent.GoBack -> goBack()
            is AddProductEvent.OnProductNameChange -> {
                _stateFlow.update {
                    it.copy(
                        newProductName = event.name
                    )
                }
                validateCurrentFormState()
            }
        }
    }

    private fun showError(error: DomainError) = viewModelScope.launch {
        snackBarController.sendEvent(
            event = SnackbarEvent(
                message = error.message ?: "Error",
                actionLabel = SnackbarAction(
                    name = "Aceptar",
                    action = {}
                )
            )
        )
    }

    private fun validateCurrentFormState() {
        _stateFlow.update {
            it.copy(
                isValidForm = it.newProductName.isNotEmpty()
            )
        }
    }

    private fun goBack() = viewModelScope.launch {
        navigator.navigateUp()
    }
}