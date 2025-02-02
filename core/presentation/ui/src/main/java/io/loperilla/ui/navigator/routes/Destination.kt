package io.loperilla.ui.navigator.routes

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.navigator
 * Created By Manuel Lopera on 2/2/25 at 17:16
 * All rights reserved 2025
 */
sealed interface Destination {
    @Serialization
    data object Home : Destination
    @Serialization
    data object Login : Destination
    @Serialization
    data object Register : Destination
}