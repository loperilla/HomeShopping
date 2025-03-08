package io.loperilla.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.loperilla.data.local.database.dao.QueryDao
import io.loperilla.data.local.database.dao.UserDao
import io.loperilla.data.local.database.entities.QueryEntity
import io.loperilla.data.local.database.entities.UserEntity

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.database
 * Created By Manuel Lopera on 26/8/23 at 19:25
 * All rights reserved 2023
 */
@Database(
    entities = [
        QueryEntity::class,
        UserEntity::class
    ],
    version = 1
)
abstract class HomeShoppingDatabase : RoomDatabase() {
    abstract fun queryDao(): QueryDao
    abstract fun userDao(): UserDao
}
