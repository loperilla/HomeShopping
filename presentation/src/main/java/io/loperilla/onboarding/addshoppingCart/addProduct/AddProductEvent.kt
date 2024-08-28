package io.loperilla.onboarding.addshoppingCart.addProduct

import android.graphics.Bitmap

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */
sealed class AddProductEvent {
    data object NavigateBack: AddProductEvent()
    data object AddProduct: AddProductEvent()
    data object ShowMenuToSelectPhoto: AddProductEvent()
    data object HideMenuToSelectPhoto: AddProductEvent()
    data class NewProductInput(val text: String): AddProductEvent()
    data class NewPhoto(val bitmap: Bitmap): AddProductEvent()
}
