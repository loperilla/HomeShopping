package io.loperilla.domain.model

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.model
 * Created By Manuel Lopera on 22/2/25 at 09:26
 * All rights reserved 2025
 */
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String
)
