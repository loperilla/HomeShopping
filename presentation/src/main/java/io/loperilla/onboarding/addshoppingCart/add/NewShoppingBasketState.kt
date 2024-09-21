package io.loperilla.onboarding.addshoppingCart.add

import io.loperilla.onboarding.navigator.routes.NavigationAction
import io.loperilla.onboarding_domain.model.database.product.Product

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 16/8/24 at 19:30
 * All rights reserved 2024
 */
data class NewShoppingBasketState(
    val searchBarActive: Boolean = false,
    val previousQueryList: List<String> = emptyList(),
    val newActionNav: NavigationAction? = null,
    val searchBarQueryValue: String = "",
    val productList: List<Product> = emptyList()
) {
    val filteredQueryList = previousQueryList.filter {
        it.contains(searchBarQueryValue)
    }.ifEmpty { previousQueryList }

    val filteredItemShoppingList = productList.filter {
        it.name.contains(
            searchBarQueryValue,
            true
        )
    }
}
