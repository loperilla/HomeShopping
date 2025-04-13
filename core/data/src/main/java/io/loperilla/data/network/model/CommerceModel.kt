package io.loperilla.data.network.model

import io.loperilla.domain.model.commerce.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network.model
 * Created By Manuel Lopera on 13/4/25 at 11:06
 * All rights reserved 2025
 */
data class CommerceModel(
    val id: String? = null,
    val name: String? = null
) {
    fun toDomain() = Commerce(
        id.orEmpty(),
        name.orEmpty()
    )

}
