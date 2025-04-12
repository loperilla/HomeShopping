package io.loperilla.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 16/3/25 at 12:37
 * All rights reserved 2025
 */
sealed interface CommerceEvent {
    data object GoBack : CommerceEvent
    data object AddNewCommerce : CommerceEvent
    data class RemoveCommerce(val id: String) : CommerceEvent
}