package io.loperilla.presentation

import io.loperilla.domain.model.ShoppingList
import io.loperilla.domain.model.User

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 19/2/25 at 19:58
 * All rights reserved 2025
 */

enum class ShoppingListBanner {
    NOT_FOUND, EMPTY, VALID
}

data class HomeState(
    val isLoading: Boolean = true,
    val lastShoppingList: ShoppingList? = null,
    val currentUser: User? = null
) {
    val shoppingListBanner: ShoppingListBanner = when {
        lastShoppingList == null -> ShoppingListBanner.NOT_FOUND
        lastShoppingList.productIdList.isNullOrEmpty() -> ShoppingListBanner.EMPTY
        else -> ShoppingListBanner.VALID
    }
    val showUserIncompleteUser: Boolean = !isLoading && currentUser?.name.isNullOrBlank()

    val userName = currentUser?.name ?: ""
}
