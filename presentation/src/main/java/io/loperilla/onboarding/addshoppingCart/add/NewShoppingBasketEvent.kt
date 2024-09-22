package io.loperilla.onboarding.addshoppingCart.add

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 16/8/24 at 19:31
 * All rights reserved 2024
 */
sealed class NewShoppingBasketEvent {
    data object NavigateBack: NewShoppingBasketEvent()
    data object AddItem: NewShoppingBasketEvent()
    data class ChangeSearchBarActive(
        val searchBarActive: Boolean
    ): NewShoppingBasketEvent()
    data class SearchQueryChanged(val query: String): NewShoppingBasketEvent()
    data class SearchShoppingProductWithCurrentValue(val query: String): NewShoppingBasketEvent()
    data class CommerceClicked(val commerce: Commerce): NewShoppingBasketEvent()
    data class QueryClicked(val query: String): NewShoppingBasketEvent()
    data class RemoveQuery(val query: String): NewShoppingBasketEvent()
    data object CleanSearchBarInputValue: NewShoppingBasketEvent()
}