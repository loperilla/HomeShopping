package io.loperilla.onboarding.addshoppingCart.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.onboarding_domain.model.database.Commerce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */

@HiltViewModel(assistedFactory = AddProductViewModelFactory::class)
class AddProductViewModel @AssistedInject constructor(
    @Assisted private val commerce: Commerce
): ViewModel() {
    private var _stateFlow: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    val stateFlow: StateFlow<AddProductState> = _stateFlow.asStateFlow()

    fun onEvent(newEvent: AddProductEvent) = viewModelScope.launch(Dispatchers.IO) {
        when(newEvent) {
            AddProductEvent.AddProduct -> TODO()
            AddProductEvent.NavigateBack -> _stateFlow.update {
                it.copy(
                    newRoute = NavAction.PopBackStack
                )
            }
            is AddProductEvent.NewPhoto -> _stateFlow.update {
                it.copy(
                    bitmapSelected = newEvent.bitmap,
                    showMenuToProductPhoto = false
                )
            }
            is AddProductEvent.NewProductInput -> _stateFlow.update {
                it.copy(
                    newProductInputValue = newEvent.text
                )
            }

            AddProductEvent.ShowMenuToSelectPhoto -> _stateFlow.update {
                it.copy(
                    showMenuToProductPhoto = true
                )
            }

            AddProductEvent.HideMenuToSelectPhoto -> _stateFlow.update {
                it.copy(
                    showMenuToProductPhoto = false
                )
            }
        }
    }
}

