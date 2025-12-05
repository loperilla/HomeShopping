package io.loperilla.testing.tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.tag
 * Created By Manuel Lopera on 30/11/25 at 12:20
 * All rights reserved 2025
 */

sealed class CommerceTags(override val name: String) : Tag {
    data object RootScreen : CommerceTags("commerce_root")
    data object TopBar : CommerceTags("commerce_topbar")
    data object Fab : CommerceTags("commerce_fab")
    data object Loading : CommerceTags("commerce_loading")
    data object Screen : CommerceTags("commerce_screen")
    data object NewInput : CommerceTags("commerce_input")
    data object List : CommerceTags("commerce_list")
    data object EmptyList : CommerceTags("commerce_empty_list")
}