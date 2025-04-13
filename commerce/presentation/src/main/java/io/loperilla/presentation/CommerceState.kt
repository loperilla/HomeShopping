package io.loperilla.presentation

import io.loperilla.domain.model.commerce.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 16/3/25 at 12:37
 * All rights reserved 2025
 */
data class CommerceState(
    val isLoading: Boolean = true,
    val showNewCommerceInput: Boolean = false,
    val newCommerceName: String = "",
    val commerceList: List<Commerce> = emptyList()
)
