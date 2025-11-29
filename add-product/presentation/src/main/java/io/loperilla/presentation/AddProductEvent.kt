package io.loperilla.presentation

sealed interface AddProductEvent {
    data object GoBack: AddProductEvent
    data class OnProductNameChange(val name: String): AddProductEvent
    data object AddNewProduct: AddProductEvent

}