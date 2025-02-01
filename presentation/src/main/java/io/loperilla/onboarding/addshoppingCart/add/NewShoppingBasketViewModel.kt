package io.loperilla.onboarding.addshoppingCart.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding.navigator.routes.Destination
import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_domain.usecase.product.GetProductsByCommerceUseCase
import io.loperilla.onboarding_domain.usecase.query.QueryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 26/8/23 at 17:15
 * All rights reserved 2023
 */

@HiltViewModel(assistedFactory = NewShoppingBasketViewModelFactory::class)
class NewShoppingBasketViewModel @AssistedInject constructor(
    @Assisted private val commerce: Commerce,
    getProductsByCommerceUseCase: GetProductsByCommerceUseCase,
    private val queryModel: QueryModel,
    private val navigator: Navigator
) : ViewModel() {
    private var _stateFlow: MutableStateFlow<NewShoppingBasketState> =
        MutableStateFlow(NewShoppingBasketState())
    val stateFlow: StateFlow<NewShoppingBasketState> = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            queryModel.getAllQueries().flowOn(Dispatchers.IO).collectLatest { queryList ->
                _stateFlow.update {
                    it.copy(
                        previousQueryList = queryList
                    )
                }
            }
            async {
                getProductsByCommerceUseCase(commerce.id).collectLatest { productList ->
                    _stateFlow.update {
                        it.copy(
                            productList = productList
                        )
                    }
                }
            }.await()
        }
    }

    fun onEvent(newEvent: NewShoppingBasketEvent) = viewModelScope.launch {
        when (newEvent) {
            NewShoppingBasketEvent.AddItem -> navigator.navigate(
                Destination.NewProduct(
                    commerce
                )
            )

            is NewShoppingBasketEvent.ChangeSearchBarActive -> {
                _stateFlow.update {
                    it.copy(
                        searchBarActive = newEvent.searchBarActive
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
            NewShoppingBasketEvent.NavigateBack -> navigator.navigateUp()

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
