package io.loperilla.onboarding.addshoppingCart

import io.loperilla.core_ui.routes.NavAction
import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_domain.model.database.ShoppingItem

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 16/8/24 at 19:30
 * All rights reserved 2024
 */
data class NewShoppingBasketState(
    val searchBarActive: Boolean = false,
    val previousQueryList: List<String> = emptyList(),
    val newActionNav: NavAction? = null,
    val searchBarQueryValue: String = "",
    val commerceList: List<Commerce> = emptyList(),
    val itemShoppingList: List<ShoppingItem> = emptyList()
) {
    val filteredQueryList = previousQueryList.filter {
        it.contains(searchBarQueryValue)
    }.ifEmpty { previousQueryList }

    val filteredItemShoppingList = itemShoppingList.filter {
        it.name.contains(
            searchBarQueryValue,
            true
        )
    }
}
