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
    data object COMMERCE : Destination

    @Serializable
    data object SELECT_COMMERCE : Destination

    @Serializable
    data class NEW(
        val commerce: Commerce
    )

    @Serializable
    data class NEW_ITEM(
        val commerce: Commerce
    )
}
