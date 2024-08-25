package io.loperilla.onboarding.home

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 10/8/24 at 11:18
 * All rights reserved 2024
 */
sealed class HomeEvent {
    data object ShowLogoutDialog : HomeEvent()
    data object HideLogoutDialog : HomeEvent()
    data object CreateShoppingBasket : HomeEvent()
    data class ItemSelected(val id: String) : HomeEvent()
    data object ChangeChipVisibility : HomeEvent()
    data object NavigateToCommerce : HomeEvent()
    data object LogOut : HomeEvent()
}