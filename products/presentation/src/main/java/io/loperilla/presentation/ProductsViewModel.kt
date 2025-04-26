package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.ui.navigator.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val navigator: Navigator
) : ViewModel() {

    private var _stateFlow: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState())
    val stateFlow = _stateFlow
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ProductsState()
        )

        
        fun onEvent(action: ProductsEvent) = viewModelScope.launch {
            when(action) {
                ProductsEvent.GoBack -> navigator.navigateUp()
                else -> TODO("Handle actions")
            }
        }

}