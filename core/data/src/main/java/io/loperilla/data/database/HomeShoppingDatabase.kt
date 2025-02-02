package io.loperilla.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.loperilla.data.database.dao.QueryDao
import io.loperilla.data.database.entities.QueryEntity

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database
 * Created By Manuel Lopera on 26/8/23 at 19:25
 * All rights reserved 2023
 */
@Database(
    entities = [
        QueryEntity::class
    ],
    version = 1
)
abstract class HomeShoppingDatabase : RoomDatabase() {
    abstract fun queryDao(): QueryDao
}
