package io.loperilla.data.firebase.database

import io.loperilla.datasource.firebase.database.ShoppingCartListFirebaseDatabase
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.firebase.database
 * Created By Manuel Lopera on 25/8/23 at 18:44
 * All rights reserved 2023
 */
class ShoppingRepository @Inject constructor(
    private val database: ShoppingCartListFirebaseDatabase
) {
    fun getAllShopping() = database.getAllShoppingBuy()
}
