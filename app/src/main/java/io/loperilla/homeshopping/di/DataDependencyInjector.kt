package io.loperilla.homeshopping.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.COMMERCES
import io.loperilla.data.PRODUCTS
import io.loperilla.data.PRODUCT_PATH
import io.loperilla.data.database.dao.QueryDao
import io.loperilla.data.impl.CommerceRepositoryImpl
import io.loperilla.data.impl.FirebaseAuthRepositoryImpl
import io.loperilla.data.impl.ProductsRepositoryImpl
import io.loperilla.data.impl.QueryRepositoryImpl
import io.loperilla.onboarding_domain.repository.AuthRepository
import io.loperilla.onboarding_domain.repository.CommerceRepository
import io.loperilla.onboarding_domain.repository.ProductRepository
import io.loperilla.onboarding_domain.repository.QueryRepository
import io.loperilla.onboarding_domain.repository.UserDataStore
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

    @Singleton
    @Provides
    fun provideStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideFirebaseAuth(
        userDataStore: UserDataStore
    ): AuthRepository = FirebaseAuthRepositoryImpl(Firebase.auth, userDataStore)

    @Provides
    @Singleton
    fun provideCommerceRepository(
        firestore: FirebaseFirestore
    ): CommerceRepository = CommerceRepositoryImpl(
        firestore.collection(COMMERCES)
    )

    @Provides
    @Singleton
    fun provideQueryRepository(
        queryDao: QueryDao
    ): QueryRepository = QueryRepositoryImpl(queryDao)

    @Provides
    @Singleton
    fun provideProductRepository(
        storage: FirebaseStorage,
        firestore: FirebaseFirestore
    ): ProductRepository = ProductsRepositoryImpl(
        storage.reference.child(PRODUCT_PATH),
        firestore.collection(PRODUCTS)
    )
}
