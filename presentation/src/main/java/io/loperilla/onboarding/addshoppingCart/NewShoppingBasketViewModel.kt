package io.loperilla.onboarding.addshoppingCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import io.loperilla.onboarding_domain.usecase.query.QueryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 26/8/23 at 17:15
 * All rights reserved 2023
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class NewShoppingBasketViewModel @Inject constructor(
    val getAllCommerceListUseCase: GetCommerceListUseCase,
    private val queryModel: QueryModel,
//    private val itemShoppingUseCase: ItemShoppingUseCase
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<NewShoppingBasketState> =
        MutableStateFlow(NewShoppingBasketState())
    val stateFlow: StateFlow<NewShoppingBasketState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            queryModel.getAllQueries().collectLatest { queryList ->
                _stateFlow.update {
                    it.copy(
                        previousQueryList = queryList
                    )
                }
            }

            async {
                getAllCommerceListUseCase().collectLatest { commerceList ->
                    _stateFlow.update {
                        it.copy(
                            commerceList = commerceList
                        )
                    }
                }
            }.await()
        }
    }

    fun onEvent(newEvent: NewShoppingBasketEvent) = viewModelScope.launch {
        when (newEvent) {
            NewShoppingBasketEvent.AddItem -> TODO()
            NewShoppingBasketEvent.ChangeSearchBarActive -> {
                _stateFlow.update {
                    it.copy(
                        searchBarActive = !it.searchBarActive
                    )
                }
            }

            NewShoppingBasketEvent.CleanSearchBarInputValue -> {
                _stateFlow.update {
                    it.copy(
                        searchBarQueryValue = ""
                    )
                }
            }

            is NewShoppingBasketEvent.CommerceClicked -> TODO()
            NewShoppingBasketEvent.NavigateBack -> _stateFlow.update {
                it.copy(
                    newActionNav = NavAction.PopBackStack
                )
            }

            is NewShoppingBasketEvent.QueryClicked -> {
                _stateFlow.update {
                    it.copy(
                        searchBarQueryValue = newEvent.query,
                        searchBarActive = false
                    )
                }
            }

            is NewShoppingBasketEvent.RemoveQuery -> withContext(Dispatchers.IO) {
                queryModel.removeQuery(newEvent.query)
            }

            is NewShoppingBasketEvent.SearchQueryChanged -> {
                _stateFlow.update {
                    it.copy(
                        searchBarQueryValue = newEvent.query
                    )
                }
            }

            is NewShoppingBasketEvent.SearchShoppingProductWithCurrentValue -> withContext(
                Dispatchers.IO
            ) {
                if (!stateFlow.value.previousQueryList.contains(newEvent.query)) {
                    queryModel.insertNewQuery(newEvent.query)
                }
                _stateFlow.update {
                    it.copy(
                        searchBarActive = false
                    )
                }

                _stateFlow.update {
                    it.copy(
                        searchBarQueryValue = newEvent.query
                    )
                }
            }
        }
    }
}
