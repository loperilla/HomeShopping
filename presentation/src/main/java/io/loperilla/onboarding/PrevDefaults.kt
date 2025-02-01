package io.loperilla.onboarding

import io.loperilla.onboarding_domain.model.database.Commerce
import io.loperilla.onboarding_domain.model.database.product.Product

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding
 * Created By Manuel Lopera on 16/8/24 at 20:11
 * All rights reserved 2024
 */

val commerces = listOf(
    Commerce("1", "MasYMas", true),
    Commerce("2", "Mercadona", false),
    Commerce("3", "Carrefour", false),
)

val products = listOf(
    Product(
        "sdfhjkl",
        name = "Manzana",
        imageUrl = "https://cdn.icon-icons.com/icons2/16/PNG/256/fruit_apple_food_1815.png"
    ),
    Product(
        "sdfhjkasdfhjkl",
        name = "Pera",
        imageUrl = "https://cdn.icon-icons.com/icons2/16/PNG/256/fruit_apple_food_1815.png"
    )
)