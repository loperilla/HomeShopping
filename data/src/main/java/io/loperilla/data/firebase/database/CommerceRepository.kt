package io.loperilla.data.firebase.database

import io.loperilla.datasource.firebase.database.CommerceDatabase
import io.loperilla.model.database.Commerce
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.firebase.database
 * Created By Manuel Lopera on 24/9/23 at 20:52
 * All rights reserved 2023
 */
class CommerceRepository @Inject constructor(
    private val database: CommerceDatabase
) {
    fun getAllShopping() = database.getAllCommerces()

    suspend fun postItem(commerce: Commerce) = database.addCommerce(commerce)
}
