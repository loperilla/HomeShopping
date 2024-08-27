package io.loperilla.onboarding.addshoppingCart.addProduct

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:56
 * All rights reserved 2024
 */
sealed class AddProductEvent {
    data object NavigateBack: AddProductEvent()
    data object AddProduct: AddProductEvent()
    data class NewProductInput(val text: String): AddProductEvent()
}
