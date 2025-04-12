package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.domain.GetCommercesUseCase
import io.loperilla.domain.model.fold
import io.loperilla.ui.navigator.Navigator
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
    private val getCommercesUseCase: GetCommercesUseCase
): ViewModel() {
    private var _stateFlow: MutableStateFlow<CommerceState> = MutableStateFlow(CommerceState())
    val stateFlow: StateFlow<CommerceState> = _stateFlow
        .onStart {
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
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CommerceState()
        )

    fun onEvent(newEvent: CommerceEvent) = viewModelScope.launch {
        when(newEvent) {
            CommerceEvent.GoBack -> navigator.navigateUp()
            CommerceEvent.AddNewCommerce -> TODO()
            is CommerceEvent.RemoveCommerce -> TODO()
        }
    }
}