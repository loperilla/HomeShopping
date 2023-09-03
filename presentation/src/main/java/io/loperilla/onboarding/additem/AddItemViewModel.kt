package io.loperilla.onboarding.additem

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 3/9/23 at 09:52
 * All rights reserved 2023
 */
@HiltViewModel
class AddItemViewModel @Inject constructor() : ViewModel() {
    private var _pagerUserInputEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val pagerUserInputEnabled: StateFlow<Boolean> = _pagerUserInputEnabled

    fun userGoesToTakeAPhoto() {
        viewModelScope.launch {
            _pagerUserInputEnabled.value = false
        }
    }

    fun userTakeAPhoto(bitmap: Bitmap) {
        viewModelScope.launch {
            _pagerUserInputEnabled.value = true
        }
    }
}
