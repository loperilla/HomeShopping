package io.loperilla.data.model

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.model
 * Created By Manuel Lopera on 11/8/24 at 12:05
 * All rights reserved 2024
 */
data class CommerceModel(
    val id: String? = null,
    val name: String? = null
) {

    fun toDomain() = Commerce(id ?: "", name ?: "")
}
