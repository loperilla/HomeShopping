package io.loperilla.data.model

import io.loperilla.onboarding_domain.model.database.product.Product

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.model
 * Created By Manuel Lopera on 28/8/24 at 14:03
 * All rights reserved 2024
 */
data class ProductModel(
    val id: String? = null,
    val name: String? = null,
    val urlImage: String? = null,
    val commerceIdList: List<String>? = null,
) {

    fun toDomain() = Product(
        id = id ?: "",
        name = name ?: "",
        imageUrl = urlImage ?: ""
    )
}
