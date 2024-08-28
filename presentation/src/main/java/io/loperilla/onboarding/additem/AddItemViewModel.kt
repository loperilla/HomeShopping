package io.loperilla.onboarding.additem

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding_domain.usecase.product.AddProductUseCase
import kotlinx.coroutines.Dispatchers
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
class AddItemViewModel @Inject constructor(
    private val itemShoppingUseCase: AddProductUseCase
) : ViewModel() {
    private var _pagerUserInputEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val pagerUserInputEnabled: StateFlow<Boolean> = _pagerUserInputEnabled

    private var _inputNameValue: MutableStateFlow<String> = MutableStateFlow("")
    val inputNameValue: StateFlow<String> = _inputNameValue

    private var _isInputValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInputValid: StateFlow<Boolean> = _isInputValid

    private var _bitmapSelected: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val bitmapSelected: StateFlow<Bitmap?> = _bitmapSelected

    private var _insertItemSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val insertItemSuccess: StateFlow<Boolean> = _insertItemSuccess

    fun userGoesToTakeAPhoto() {
        viewModelScope.launch {
            _pagerUserInputEnabled.value = false
        }
    }

    fun userTakeAPhoto(bitmap: Bitmap) {
        viewModelScope.launch {
            _pagerUserInputEnabled.value = true
            _bitmapSelected.value = bitmap
        }
    }

    fun inputChange(newValue: String, isValidInput: Boolean) {
        viewModelScope.launch {
            _isInputValid.value = isValidInput
            _inputNameValue.value = newValue
        }
    }

    fun addProduct() {
        viewModelScope.launch(Dispatchers.IO) {
//            when (val result = itemShoppingUseCase.addItem(
//                ShoppingItem(
//                    name = inputNameValue.value
//                ), bitmapSelected.value!!.toByteArray()
//            )) {
//                is PostDatabaseResult.FAIL -> {
//                    Timber.e(result.exception)
//                }
//
//                PostDatabaseResult.SUCCESS -> {
//                    Timber.i("${inputNameValue.value} fue añadido con éxito")
//                    _insertItemSuccess.value = true
//                }
//            }
        }
    }
}
