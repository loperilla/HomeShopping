package io.loperilla.onboarding.commerce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.NavAction
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
 * From: io.loperilla.onboarding.commerce
 * Created By Manuel Lopera on 11/8/24 at 19:45
 * All rights reserved 2024
 */
@HiltViewModel
class CommerceViewModel @Inject constructor(
    getCommerceListUseCase: GetCommerceListUseCase
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<CommerceState> = MutableStateFlow(CommerceState())
    val stateFlow: StateFlow<CommerceState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCommerceListUseCase().collectLatest { commerceList ->
                _stateFlow.update {
                    it.copy(
                        list = commerceList
                    )
                }
            }
        }
    }

    fun onEvent(newEvent: CommerceEvent) = viewModelScope.launch (Dispatchers.IO) {
        when (newEvent) {
            is CommerceEvent.GoBack -> {
                _stateFlow.update {
                    it.copy(
                        newRoute = NavAction.PopBackStack
                    )
                }
            }

            is CommerceEvent.DeleteCommerce -> {
                _stateFlow.update {
                    it.copy(
                        list = it.list.filter {
                            itemIterated -> itemIterated.id != newEvent.id
                        }
                    )
                }
            }
        }
    }
}