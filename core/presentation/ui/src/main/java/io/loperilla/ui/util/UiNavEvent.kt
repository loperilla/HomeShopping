package io.loperilla.ui.util

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.util
 * Created By Manuel Lopera on 21/4/23 at 20:15
 * All rights reserved 2023
 */
sealed class UiNavEvent {
    data class Navigate(val route: String) : UiNavEvent()
    object NavigateUp : UiNavEvent()
}
