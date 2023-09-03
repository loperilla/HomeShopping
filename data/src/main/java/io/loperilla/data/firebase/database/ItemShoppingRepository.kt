package io.loperilla.data.firebase.database

import io.loperilla.datasource.firebase.database.ShoppingItemListFirebaseDatabase
import io.loperilla.model.database.ShoppingItem
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.firebase.database
 * Created By Manuel Lopera on 30/8/23 at 21:08
 * All rights reserved 2023
 */
class ItemShoppingRepository @Inject constructor(
    private val database: ShoppingItemListFirebaseDatabase
) {
    fun getAllShopping() = database.getAllItems()

    suspend fun postItem(item: ShoppingItem, bitmapToUpload: ByteArray) = database.addItemShopping(item, bitmapToUpload)
}
