package io.loperilla.presentation

import io.loperilla.domain.model.product.Product

data class ProductsState(
    val isLoading: Boolean = false,
    val productList: List<Product> = emptyList()
)