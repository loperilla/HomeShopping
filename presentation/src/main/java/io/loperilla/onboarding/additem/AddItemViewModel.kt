package io.loperilla.onboarding.additem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.model.database.Commerce
import io.loperilla.model.database.result.ReadDatabaseResult
import io.loperilla.onboarding_domain.usecase.commerce.CommerceUseCase
import io.loperilla.onboarding_domain.usecase.itemShopping.ItemShoppingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val itemShoppingUseCase: ItemShoppingUseCase,
    private val commerceUseCase: CommerceUseCase
) : ViewModel() {
    private var _state: MutableStateFlow<AddItemState> = MutableStateFlow(AddItemState())
    val state: StateFlow<AddItemState> = _state

    private var _commerceList: MutableStateFlow<List<Commerce>> = MutableStateFlow(emptyList())
    val commerceList: StateFlow<List<Commerce>> = _commerceList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            commerceUseCase().collectLatest { list ->
                when (list) {
                    is ReadDatabaseResult.FAIL -> {

                    }

                    is ReadDatabaseResult.SUCCESS -> {
                        _commerceList.value = list.result
                    }
                }
            }
        }
    }

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

                is AddItemEvent.NewDropdownExpandedState -> _state.value = _state.value.copy(
                    isDropdownExpanded = newEvent.newState,
                    addItemRequestState = AddItemRequestState.NONE
                )

                AddItemEvent.ShowAddCommerce -> {
                    _state.value = _state.value.copy(
                        addCommerceClicked = true
                    )
                }

                is AddItemEvent.CreateNewCommerce -> {
                    commerceUseCase.addCommerce(Commerce(name = newEvent.newCommerceName))
                    _state.value = _state.value.copy(
                        addCommerceClicked = false
                    )
                }

                AddItemEvent.DismissNewCommerceDialog -> {
                    _state.value = _state.value.copy(
                        addCommerceClicked = false
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
