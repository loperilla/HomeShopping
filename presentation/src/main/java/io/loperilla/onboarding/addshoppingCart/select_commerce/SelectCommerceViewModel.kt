package io.loperilla.onboarding.addshoppingCart.select_commerce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.select_commerce
 * Created By Manuel Lopera on 25/8/24 at 11:16
 * All rights reserved 2024
 */

@HiltViewModel
class SelectCommerceViewModel @Inject constructor(
    getCommerceListUseCase: GetCommerceListUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<SelectCommerceState> = MutableStateFlow(SelectCommerceState())
    val stateFlow: StateFlow<SelectCommerceState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCommerceListUseCase().collectLatest { commerceList ->
                _stateFlow.update {
                    it.copy(
                        commerceList = commerceList
                    )
                }
            }
        }
    }
    fun onEvent(newEvent: SelectCommerceEvent) {
        when(newEvent) {
            SelectCommerceEvent.OnBack -> _stateFlow.update {
                it.copy(
                    backPressed = true
                )
            }
            is SelectCommerceEvent.SelectCommerce -> _stateFlow.update {
                it.copy(commerceSelected = newEvent.commerce)
            }
        }
    }
}
