package io.loperilla.onboarding_domain.usecase.itemShopping

import io.loperilla.data.firebase.database.ItemShoppingRepository
import io.loperilla.model.database.ShoppingItem
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.itemShopping
 * Created By Manuel Lopera on 31/8/23 at 16:39
 * All rights reserved 2023
 */
class ItemShoppingUseCase @Inject constructor(
    private val repository: ItemShoppingRepository
) {
    suspend fun addItem(item: ShoppingItem, bitmapToUpload: ByteArray) = repository.postItem(item, bitmapToUpload)

    fun getItems() = repository.getAllShopping()
}
