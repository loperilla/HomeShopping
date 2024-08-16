package io.loperilla.core_ui.routes

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.routes
 * Created By Manuel Lopera on 22/4/23 at 18:01
 * All rights reserved 2023
 */
sealed class Routes(val route: String) {
    data object AUTH : Routes("Auth Screen") {
        data object LOGIN : Routes("login")
        data object REGISTER : Routes("register")
    }

    data object HOME : Routes("Home Screen")

    data object COMMERCE : Routes("Commerce Screen")

    data object SHOPPING_BASKET : Routes("ShoppingBasket Screen") {
        data object ADD_SHOPPING : Routes("Add Shopping Cart")
        data object NEW_ITEM : Routes("New Item")
    }
}