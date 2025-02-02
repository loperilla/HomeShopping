package io.loperilla.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database.entities
 * Created By Manuel Lopera on 26/8/23 at 19:31
 * All rights reserved 2023
 */
@Entity
data class QueryEntity(
    @PrimaryKey val query: String
)
