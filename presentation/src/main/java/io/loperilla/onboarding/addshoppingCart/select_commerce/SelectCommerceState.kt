package io.loperilla.onboarding.addshoppingCart.select_commerce

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.select_commerce
 * Created By Manuel Lopera on 25/8/24 at 11:16
 * All rights reserved 2024
 */
data class SelectCommerceState(
    val commerceList: List<Commerce> = emptyList()
)