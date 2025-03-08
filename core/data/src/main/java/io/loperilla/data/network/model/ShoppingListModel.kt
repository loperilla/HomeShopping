package io.loperilla.data.network.model

import io.loperilla.domain.model.ShoppingList

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network.model
 * Created By Manuel Lopera on 8/3/25 at 13:54
 * All rights reserved 2025
 */
data class ShoppingListModel(
    val id: String? = null,
    val productIdList: List<String>? = null
) {
    fun toDomain() = ShoppingList(
        id.orEmpty(),
        productIdList ?: emptyList()
    )
}
