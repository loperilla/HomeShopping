package io.loperilla.ui.navigator.routes

import kotlinx.serialization.Serializable

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.navigator
 * Created By Manuel Lopera on 2/2/25 at 17:16
 * All rights reserved 2025
 */

sealed interface Destination {
    @Serializable
    data object Home : Destination
    @Serializable
    data object Login : Destination
    @Serializable
    data object Register : Destination
}