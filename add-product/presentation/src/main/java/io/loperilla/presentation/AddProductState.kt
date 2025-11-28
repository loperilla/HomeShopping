package io.loperilla.presentation

data class AddProductState(
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false,
    val newProductName: String = "",
)