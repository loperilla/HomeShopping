package io.loperilla.presentation

sealed interface ProductsEvent {
    data object GoBack: ProductsEvent
    data object AddNewProduct: ProductsEvent

    data class  RemoveProduct(val id: String): ProductsEvent
}