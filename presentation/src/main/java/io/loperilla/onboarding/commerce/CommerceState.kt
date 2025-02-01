package io.loperilla.onboarding.commerce

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.commerce
 * Created By Manuel Lopera on 11/8/24 at 19:45
 * All rights reserved 2024
 */
data class CommerceState (
    val list: List<Commerce> = emptyList(),
    val showNewCommerceForm: Boolean = false,
    val newCommerceInputValue: String = ""
)