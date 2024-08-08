package io.loperilla.datasource.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.loperilla.datasource.database.HomeShoppingDatabase
import javax.inject.Singleton

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.di
 * Created By Manuel Lopera on 26/8/23 at 19:27
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideGameDatabase(
        @ApplicationContext context: Context
    ): HomeShoppingDatabase = Room
        .databaseBuilder(
            context,
            HomeShoppingDatabase::class.java,
            HomeShoppingDatabase::class.java.simpleName
        )
        .build()


    @Provides
    @Singleton
    fun provideQueryDao(
        database: HomeShoppingDatabase
    ) = database.queryDao()

}
