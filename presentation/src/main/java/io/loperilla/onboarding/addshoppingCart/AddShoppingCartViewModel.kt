package io.loperilla.onboarding.addshoppingCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.model.database.ShoppingItem
import io.loperilla.model.database.result.PostDatabaseResult
import io.loperilla.model.database.result.ReadDatabaseResult
import io.loperilla.onboarding_domain.usecase.itemShopping.ItemShoppingUseCase
import io.loperilla.onboarding_domain.usecase.shoppingcart.QueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 26/8/23 at 17:15
 * All rights reserved 2023
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class AddShoppingCartViewModel @Inject constructor(
    private val queryUseCase: QueryUseCase,
    private val itemShoppingUseCase: ItemShoppingUseCase
) : ViewModel() {
    private var _searchInputQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchInputQuery: StateFlow<String> = _searchInputQuery
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    private var _searchBarActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchBarActive: StateFlow<Boolean> = _searchBarActive.asStateFlow()

    private var _currentShoppingCartCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentShoppingCartCount: StateFlow<Int> = _currentShoppingCartCount

    private var _queryList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val queryList: StateFlow<List<String>> = _queryList

    private var _itemShoppingList: MutableStateFlow<List<ShoppingItem>> = MutableStateFlow(emptyList())
    val itemShoppingList: StateFlow<List<ShoppingItem>> = _itemShoppingList

    private var _isAddItemFABClicked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAddItemFABClicked: StateFlow<Boolean> = _isAddItemFABClicked

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val deferred = listOf(
                async {
                    queryUseCase(searchInputQuery.value).collect {
                        _queryList.value = it
                    }
                },
                async {
                    itemShoppingUseCase.getItems()
                        .collect { result ->
                            when (result) {
                                is ReadDatabaseResult.FAIL -> {
                                    Timber.e(result.errorMessage)
                                }

                                is ReadDatabaseResult.SUCCESS -> {
                                    Timber.i(
                                        result.result.toString()
                                    )
                                    _itemShoppingList.value = result.result
                                }
                            }
                        }
                }
            )
            deferred.awaitAll()
        }
    }

    fun searchInputChange(newValue: String) {
        viewModelScope.launch {
            _searchInputQuery.value = newValue
        }
    }

    fun changeInputActive(newValue: Boolean) {
        viewModelScope.launch {
            _searchBarActive.value = newValue
        }
    }

    fun removeInputQueryClicked() {
        viewModelScope.launch {
            _searchInputQuery.value = ""
        }
    }

    fun searchShoppingProductWithCurrentValue(querySearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchBarActive.value = false
            queryUseCase.insertNewQuery(querySearch)
            _searchInputQuery.value = querySearch
        }
    }

    fun onFabClicked() {
        viewModelScope.launch {
            _isAddItemFABClicked.value = true
        }
    }

    fun closeDialog() {
        viewModelScope.launch {
            _isAddItemFABClicked.value = false
        }
    }

    fun createItem(name: String, url: String) {
        viewModelScope.launch {
            closeDialog()
            when (val result = itemShoppingUseCase.addItem(ShoppingItem(name = name, imageUrl = url))) {
                is PostDatabaseResult.FAIL -> {
                    Timber.e(result.exception)
                }

                PostDatabaseResult.SUCCESS -> {
                    Timber.i("$name fue añadido con éxito")
                }
            }
        }
    }
}
