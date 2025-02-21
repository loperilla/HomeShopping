package io.loperilla.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 19/2/25 at 19:59
 * All rights reserved 2025
 */
sealed interface HomeEvent {
    data object LogOut : HomeEvent
}
