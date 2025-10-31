package io.loperilla.presentation

data class AddProductState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)