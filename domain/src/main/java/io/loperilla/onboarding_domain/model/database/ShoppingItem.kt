package io.loperilla.onboarding_domain.model.database

/*****
 * Project: CompraCasa
 * From: io.loperilla.model.database
 * Created By Manuel Lopera on 28/4/23 at 08:06
 * All rights reserved 2023
 */
data class ShoppingItem(
    var key: String? = null,
    val name: String = "",
    val imageUrl: String = ""
) {
    fun imageRef() = "${this.name}.jpg"
}
