package io.loperilla.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.firebase.auth.FirebaseAuthRepositoryImpl
import io.loperilla.data.repository.QueryRepository
import io.loperilla.data.repository.auth.AuthRepository
import io.loperilla.datasource.database.dao.QueryDao
import io.loperilla.datasource.datastore.UserDataStore

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
    fun provideFirebaseAuth(
        userDataStore: UserDataStore
    ): AuthRepository = FirebaseAuthRepositoryImpl(Firebase.auth, userDataStore)
//
//    @Provides
//    fun provideShoppingDatabase(
//        shoppingCartListFirebaseDatabase: ShoppingCartListFirebaseDatabase
//    ): ShoppingRepository = ShoppingRepository(shoppingCartListFirebaseDatabase)

    @Provides
    fun provideQueryRepository(
        queryDao: QueryDao
    ): QueryRepository = QueryRepository(queryDao)
//
//    @Provides
//    fun provideItemShoppingRepository(
//        itemListFirebaseDatabase: ShoppingItemListFirebaseDatabase
//    ): ItemShoppingRepository = ItemShoppingRepository(itemListFirebaseDatabase)
}
