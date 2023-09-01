package io.loperilla.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.datastore.DataStoreRepository
import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import io.loperilla.data.firebase.database.ItemShoppingRepository
import io.loperilla.data.firebase.database.ShoppingRepository
import io.loperilla.data.repository.QueryRepository
import io.loperilla.datasource.database.dao.QueryDao
import io.loperilla.datasource.datastore.UserDataStoreDataSourceImpl
import io.loperilla.datasource.firebase.auth.FirebaseAuthDataSourceImpl
import io.loperilla.datasource.firebase.database.ShoppingCartListFirebaseDatabase
import io.loperilla.datasource.firebase.database.ShoppingItemListFirebaseDatabase

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 23/4/23 at 18:02
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object DataDependencyInjector {
    @Provides
    fun providesDataStoreRepository(
        userPref: UserDataStoreDataSourceImpl
    ): DataStoreRepository = DataStoreRepository(userPref)

    @Provides
    fun provideFirebaseAuth(
        firebaseAuth: FirebaseAuthDataSourceImpl
    ): FirebaseAuthRepository = FirebaseAuthRepository(firebaseAuth)

    @Provides
    fun provideShoppingDatabase(
        shoppingCartListFirebaseDatabase: ShoppingCartListFirebaseDatabase
    ): ShoppingRepository = ShoppingRepository(shoppingCartListFirebaseDatabase)

    @Provides
    fun provideQueryRepository(
        queryDao: QueryDao
    ): QueryRepository = QueryRepository(queryDao)

    @Provides
    fun provideItemShoppingRepository(
        itemListFirebaseDatabase: ShoppingItemListFirebaseDatabase
    ): ItemShoppingRepository = ItemShoppingRepository(itemListFirebaseDatabase)
}
