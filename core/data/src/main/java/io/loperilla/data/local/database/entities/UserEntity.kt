package io.loperilla.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.local.database.entities
 * Created By Manuel Lopera on 22/2/25 at 09:52
 * All rights reserved 2025
 */
@Entity
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String
)
