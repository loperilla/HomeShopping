package io.loperilla.onboarding.addshoppingCart.addProduct

import android.graphics.Bitmap
import io.loperilla.core_ui.routes.NavAction
import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */
data class AddProductState(
    val newProductInputValue: String = "",
    val showMenuToProductPhoto: Boolean = false,
    val commerceListIsVisible: Boolean = false,
    val commerceList: List<Commerce> = emptyList(),
    val bitmapSelected: Bitmap? = null,
    val newRoute: NavAction? = null
) {
    private val hasSelectAlmostOneCommerce = commerceList.any { it.isSelected }
    val validProduct = newProductInputValue.isNotEmpty() && hasSelectAlmostOneCommerce
}
