package io.loperilla.onboarding_domain.model.database.product

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.model.database.product
 * Created By Manuel Lopera on 27/8/24 at 19:28
 * All rights reserved 2024
 */
data class ProductDto(
    val name: String,
    val commerceIdList: List<String>,
    val urlImage: String? = null
) {
    fun buildImageName() = "${this.name}.jpg"
}
