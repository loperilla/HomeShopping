package io.loperilla.onboarding.navigator.routes

import io.loperilla.onboarding_domain.model.database.Commerce
import kotlinx.serialization.Serializable

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.routes
 * Created By Manuel Lopera on 22/4/23 at 18:01
 * All rights reserved 2023
 */

sealed interface Destination {

    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object Login : Destination

    @Serializable
    data object Register : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data object CommerceDest : Destination

    @Serializable
    data object NewBasketGraph : Destination

    @Serializable
    data object SelectCommerce : Destination

    @Serializable
    data class NewBasketFromCommerce(
        val commerce: Commerce
    ): Destination

    @Serializable
    data class NewProduct(
        val commerce: Commerce
    ): Destination
}
