package io.loperilla.onboarding.addshoppingCart.select_commerce

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.select_commerce
 * Created By Manuel Lopera on 25/8/24 at 11:17
 * All rights reserved 2024
 */
sealed class SelectCommerceEvent {
    data object OnBack : SelectCommerceEvent()
    data class SelectCommerce(val commerce: Commerce) : SelectCommerceEvent()
}