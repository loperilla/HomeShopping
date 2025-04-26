package io.loperilla.presentation

sealed interface ProductsEvent {
    data object GoBack: ProductsEvent
}