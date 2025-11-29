package io.loperilla.data.network.model

import io.loperilla.domain.model.product.Product

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network.model
 * Created By Manuel Lopera on 4/10/25 at 12:15
 * All rights reserved 2025
 */
data class ProductModel(
    val id: String? = null,
    val productName: String? = null,
) {
    fun toDomain() = Product(
        id.orEmpty(),
        productName.orEmpty()
    )
}