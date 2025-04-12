package io.loperilla.domain.repository

import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.ShoppingList

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.repository
 * Created By Manuel Lopera on 21/2/25 at 16:43
 * All rights reserved 2025
 */
interface ShoppingListRepository {
    suspend fun getLastShoppingList(): DomainResult<ShoppingList>
}
