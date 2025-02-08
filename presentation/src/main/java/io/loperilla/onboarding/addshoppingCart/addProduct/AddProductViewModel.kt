package io.loperilla.onboarding.addshoppingCart.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_domain.model.database.product.ProductDto
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import io.loperilla.onboarding_domain.usecase.product.AddProductUseCase
import io.loperilla.ui.util.BitmapUtils.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */

(assistedFactory = AddProductViewModelFactory::class)
class AddProductViewModel @AssistedInject constructor(
    @Assisted private val commerce: Commerce,
    getAllCommerces: GetCommerceListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val navigator: Navigator
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    val stateFlow: StateFlow<AddProductState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCommerces().collectLatest { commerceList ->
                val commerceListWithSelected = commerceList.map { it.copy(isSelected = it.id == commerce.id) }
                _stateFlow.update {
                    it.copy(
                        commerceList = commerceListWithSelected
                    )
                }
            }
        }
    }

    fun onEvent(newEvent: AddProductEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (newEvent) {
            AddProductEvent.AddProduct -> {
                val productDto = ProductDto(
                    name = stateFlow.value.newProductInputValue,
                    commerceIdList = stateFlow.value.commerceList
                        .filter(Commerce::isSelected)
                        .map(Commerce::id)
                )

                addProductUseCase(productDto, stateFlow.value.bitmapSelected?.toByteArray())
                    .fold(
                        onSuccess = { navigator.navigateUp() },
                        onFailure = { it.printStackTrace() }
                    )
            }
            AddProductEvent.NavigateBack -> navigator.navigateUp()

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

            AddProductEvent.ChangeChipVisibility -> _stateFlow.update {
                it.copy(
                    commerceListIsVisible = !it.commerceListIsVisible
                )
            }
            is AddProductEvent.SelectCommerce -> {
                val newList = stateFlow.value.commerceList.map { commerce ->
                    if (commerce.id == newEvent.commerceId) {
                        commerce.copy(isSelected = !commerce.isSelected)
                    } else {
                        commerce
                    }
                }

                _stateFlow.update {
                    it.copy(
                        commerceList = newList
                    )
                }
            }
        }
    }
}

