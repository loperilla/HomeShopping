package io.loperilla.datasource.firebase.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.datasource.datastore.IUserDataStoreDataSource
import io.loperilla.datasource.firebase.auth.FirebaseAuthDataSourceImpl
import io.loperilla.datasource.firebase.auth.IFirebaseAuthDataSource
import io.loperilla.datasource.firebase.database.ShoppingCartListFirebaseDatabase
import io.loperilla.datasource.firebase.database.ShoppingItemListFirebaseDatabase
import io.loperilla.datasource.firebase.reference.CustomReference
import io.loperilla.model.database.Constants
import javax.inject.Named
import javax.inject.Singleton

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.di
 * Created By Manuel Lopera on 30/8/23 at 20:52
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseDi {
    @Singleton
    @Provides
    fun providesFirebaseAuth(
        userDataStore: IUserDataStoreDataSource
    ): IFirebaseAuthDataSource = FirebaseAuthDataSourceImpl(userDataStore)

    @Singleton
    @Provides
    fun providesShoppingListReference(
        @Named(Constants.SHOPPINGLIST) databaseReference: DatabaseReference
    ): CustomReference.SHOPPING_LIST_REFERENCE = CustomReference.SHOPPING_LIST_REFERENCE(databaseReference)

    @Named(Constants.SHOPPINGLIST)
    @Singleton
    @Provides
    fun providesShoppingListDatabase(): DatabaseReference =
        Firebase.database.reference.child(Constants.SHOPPINGLIST)

    @Singleton
    @Provides
    fun providesItemShoppingReference(
        @Named(Constants.ITEMS) databaseReference: CollectionReference,
        @Named(Constants.IMAGES) storageReference: StorageReference
    ): CustomReference.SHOPPING_ITEM_LIST_COLLECTION =
        CustomReference.SHOPPING_ITEM_LIST_COLLECTION(databaseReference, storageReference)

    @Singleton
    @Provides
    fun providesShoppingCartDatabase(
        dataStore: IUserDataStoreDataSource,
        shoppingRef: CustomReference.SHOPPING_LIST_REFERENCE,
    ) = ShoppingCartListFirebaseDatabase(dataStore, shoppingRef)

    @Named(Constants.ITEMS)
    @Singleton
    @Provides
    fun providesItemsShoppingListCollection(): CollectionReference =
        Firebase.firestore.collection(Constants.ITEMS)

    @Singleton
    @Provides
    fun providesItemShoppingDatabase(
        itemShoppingReference: CustomReference.SHOPPING_ITEM_LIST_COLLECTION
    ) = ShoppingItemListFirebaseDatabase(itemShoppingReference)

    @Named(Constants.IMAGES)
    @Singleton
    @Provides
    fun provideFirebaseStorage() = Firebase.storage.reference.child(Constants.IMAGES)
}