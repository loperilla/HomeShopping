package io.loperilla.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/3/25 at 17:35
 * All rights reserved 2025
 */
sealed interface UserDetailEvent {
    data class OnNameChanged(val newName: String) : UserDetailEvent
    data object OnBackPressed: UserDetailEvent
}