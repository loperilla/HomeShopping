package io.loperilla.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class AddProductViewModel : ViewModel() {
    private var _stateFlow: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    val stateFlow: StateFlow<AddProductState> = _stateFlow
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AddProductState()
        )

    fun onEvent(event: AddProductEvent) {
        when (event) {
            else -> TODO("Handle actions")
        }
    }

}