package io.loperilla.onboarding_domain.model.database

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.model.database
 * Created By Manuel Lopera on 10/8/24 at 16:02
 * All rights reserved 2024
 */
data class Commerce(
    val id: String,
    val name: String,
    val isSelected: Boolean = true
)
