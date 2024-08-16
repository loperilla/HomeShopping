package io.loperilla.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.firebase.auth.FirebaseAuthRepositoryImpl
import io.loperilla.data.firebase.database.COMMERCES
import io.loperilla.data.firebase.database.CommerceRepositoryImpl
import io.loperilla.data.repository.QueryRepository
import io.loperilla.data.repository.auth.AuthRepository
import io.loperilla.data.repository.database.CommerceRepository
import io.loperilla.datasource.database.dao.QueryDao
import io.loperilla.datasource.datastore.UserDataStore
import javax.inject.Singleton

/*****
 * Project: CompraCasa
 * From: io.loperilla.data.di
 * Created By Manuel Lopera on 23/4/23 at 18:02
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object DataDependencyInjector {
    @Singleton
    @Provides
    fun providesFirestoreInstance(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseAuth(
        userDataStore: UserDataStore
    ): AuthRepository = FirebaseAuthRepositoryImpl(Firebase.auth, userDataStore)

    @Singleton
    @Provides
    fun provideCommerceRepository(
        firestore: FirebaseFirestore
    ): CommerceRepository = CommerceRepositoryImpl(
        firestore.collection(COMMERCES)
    )
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
