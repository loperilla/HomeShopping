package io.loperilla.data.network

import io.loperilla.domain.model.DomainError
import io.loperilla.domain.model.DomainResult
import io.loperilla.domain.model.ShoppingList
import io.loperilla.domain.repository.ShoppingListRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network
 * Created By Manuel Lopera on 21/2/25 at 16:46
 * All rights reserved 2025
 */
class ShoppingListRepositoryImpl(
    private val shoppingListCollection: ShoppingListCollection
): ShoppingListRepository {
    override suspend fun getLasShoppingList(): DomainResult<ShoppingList> {
        return DomainResult.Error(DomainError.UnknownError())
    }
}