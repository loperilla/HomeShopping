package io.loperilla.core_ui.routes

/*****
 * Project: CompraCasa
 * From: io.loperilla.core_ui.routes
 * Created By Manuel Lopera on 22/4/23 at 18:01
 * All rights reserved 2023
 */
sealed class Routes(val route: String) {
    data object AUTH : Routes("auth_screen") {
        data object LOGIN : Routes("login")
        data object REGISTER : Routes("register")
    }

    data object HOME : Routes("home")

    data object COMMERCE : Routes("commerce")

    data object SHOPPING_BASKET : Routes("shopping_basket") {
        data object SELECT_COMMERCE : Routes("select_commerce")
        data object NEW : Routes("new/{id}/{name}") {
            fun createRoute(id: String, name: String) = this
                .route
                .replace("{id}", id)
                .replace("{name}", name)
        }
        data object NEW_ITEM : Routes("new_item/{id}/{name}") {
            fun createRoute(id: String, name: String) = this
                .route
                .replace("{id}", id)
                .replace("{name}", name)
        }
    }
}
