package io.loperilla.onboarding.commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.commerce
 * Created By Manuel Lopera on 11/8/24 at 19:46
 * All rights reserved 2024
 */
sealed class CommerceEvent {
    data object GoBack: CommerceEvent()
    data class DeleteCommerce(val id: String): CommerceEvent()
}