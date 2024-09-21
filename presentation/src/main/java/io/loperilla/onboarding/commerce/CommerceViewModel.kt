package io.loperilla.onboarding.commerce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import io.loperilla.onboarding_domain.usecase.commerce.InsertNewCommerceUseCase
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
    getCommerceListUseCase: GetCommerceListUseCase,
    private val addCommerceListUseCase: InsertNewCommerceUseCase,
    private val navigator: Navigator
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

    fun onEvent(newEvent: CommerceEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (newEvent) {
            is CommerceEvent.GoBack -> navigator.navigateUp()

            is CommerceEvent.DeleteCommerce -> {
                _stateFlow.update {
                    it.copy(
                        list = it.list.filter { itemIterated ->
                            itemIterated.id != newEvent.id
                        }
                    )
                }
            }

            CommerceEvent.ShowNewCommerceForm -> _stateFlow.update {
                it.copy(
                    showNewCommerceForm = true
                )
            }

            is CommerceEvent.NewCommerceInputChanged -> _stateFlow.update {
                it.copy(
                    newCommerceInputValue = newEvent.value
                )
            }

            CommerceEvent.AddCommerce -> {
                addCommerceListUseCase.invoke(stateFlow.value.newCommerceInputValue).onSuccess {
                    _stateFlow.update {
                        it.copy(
                            newCommerceInputValue = "",
                        )
                    }
                }
            }
        }
    }
}