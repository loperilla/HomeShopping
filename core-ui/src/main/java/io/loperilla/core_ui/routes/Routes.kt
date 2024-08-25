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

    data object SHOPPING_BASKET : Routes("ShoppingBasket Navigation") {
        data object SELECT_COMMERCE : Routes("Select Commerce")
        data object NEW : Routes("Shopping Basket From/{id}/{name}") {
            fun createRoute(id: String, name: String) = this
                .route
                .replace("{id}", id)
                .replace("{name}", name)
        }
        data object NEW_ITEM : Routes("New Item")
    }
}
