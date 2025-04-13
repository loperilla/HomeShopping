package io.loperilla.domain.usecase

import io.loperilla.domain.repository.ShoppingListRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase
 * Created By Manuel Lopera on 21/2/25 at 16:42
 * All rights reserved 2025
 */
class GetLastShoppingListUseCase(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke() = repository.getLastShoppingList()
}