package io.loperilla.onboarding.additem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding_domain.usecase.itemShopping.ItemShoppingUseCase
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
    private val itemShoppingUseCase: ItemShoppingUseCase
) : ViewModel() {
    private var _state: MutableStateFlow<AddItemState> = MutableStateFlow(AddItemState())
    val state: StateFlow<AddItemState> = _state

    fun onEvent(newEvent: AddItemEvent) {
        viewModelScope.launch {
            when (newEvent) {
                is AddItemEvent.BitmapWasSelected -> {
                    _state.value = _state.value.copy(
                        bitmap = newEvent.newBitmap,
                        addItemRequestState = AddItemRequestState.NONE
                    )
                }

                AddItemEvent.DisablePager -> {
                    _state.value = _state.value.copy(
                        isPagerEnabled = false,
                        addItemRequestState = AddItemRequestState.NONE
                    )
                }

                AddItemEvent.EnablePager -> {
                    _state.value = _state.value.copy(
                        isPagerEnabled = true,
                        addItemRequestState = AddItemRequestState.NONE
                    )
                }

                is AddItemEvent.ProductNameChanged -> {
                    _state.value = _state.value.copy(
                        productName = newEvent.productName,
                        addItemRequestState = AddItemRequestState.NONE
                    )
                }

                AddItemEvent.AddProductButtonClicked -> {
                    addProduct()
                }

                is AddItemEvent.CommerceClicked -> {
                    _state.value = _state.value.copy(
                        commerceSelected = newEvent.commerceName,
                        addItemRequestState = AddItemRequestState.NONE
                    )
                }
            }
        }
    }

    private fun addProduct() {
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
