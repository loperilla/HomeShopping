package io.loperilla.onboarding.addshoppingCart.addProduct

import android.graphics.Bitmap
import io.loperilla.core_ui.routes.NavAction

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */
data class AddProductState(
    val newProductInputValue: String = "",
    val showMenuToProductPhoto: Boolean = false,
    val bitmapSelected: Bitmap? = null,
    val newRoute: NavAction? = null
)
