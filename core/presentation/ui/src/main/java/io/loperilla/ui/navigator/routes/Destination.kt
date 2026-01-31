package io.loperilla.ui.navigator.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.navigator
 * Created By Manuel Lopera on 2/2/25 at 17:16
 * All rights reserved 2025
 */

sealed interface Destination: NavKey {
    @Serializable
    data object AuthGraph : Destination
    @Serializable
    data object Welcome : Destination
    @Serializable
    data object Login : Destination
    @Serializable
    data object Register : Destination
    @Serializable
    data object Home : Destination
    @Serializable
    data object UserDetail : Destination
    @Serializable
    data object Commerce : Destination
    @Serializable
    data object Products : Destination
    @Serializable
    data object AddProducts : Destination
}