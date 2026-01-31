package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.RemoveProductUseCase
import io.loperilla.domain.model.fold
import io.loperilla.domain.usecase.products.GetAllProductsUseCase
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val navigator: Navigator,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val removeProductUseCase: RemoveProductUseCase
) : ViewModel() {

    private var _stateFlow: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState())
    val stateFlow = _stateFlow
        .onStart {
            getAllProducts()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ProductsState()
        )

    fun getAllProducts() = viewModelScope.launch {
        getAllProductsUseCase().fold(
            onSuccess = { productList ->
                _stateFlow.update { state ->
                    state.copy(
                        isLoading = false,
                        productList = productList
                    )
                }
            },
            onError = {
//                onEvent(ProductsEvent.GoBack)
            }
        )
    }
    fun onEvent(action: ProductsEvent) = viewModelScope.launch {
        when (action) {
            ProductsEvent.GoBack -> navigator.navigateUp()
            ProductsEvent.AddNewProduct -> navigator.navigateTo(Destination.AddProducts)
            is ProductsEvent.RemoveProduct -> removeProduct(action.id)
        }
    }

    private fun removeProduct(productId: String) = viewModelScope.launch {
        _stateFlow.update { state ->
            state.copy(
                isLoading = true
            )
        }
        removeProductUseCase(productId).fold(
            onSuccess = {
                getAllProducts()
            },
            onError = {

            }
        )
    }

}