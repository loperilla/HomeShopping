package io.loperilla.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.loperilla.data.local.database.entities.UserEntity

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.local.database.dao
 * Created By Manuel Lopera on 22/2/25 at 09:52
 * All rights reserved 2025
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}