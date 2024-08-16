package io.loperilla.datasource.firebase.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*****
 * Project: HomeShopping
 * From: io.loperilla.datasource.firebase.di
 * Created By Manuel Lopera on 30/8/23 at 20:52
 * All rights reserved 2023
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseDi {
//
//    @Singleton
//    @Provides
//    fun providesShoppingListReference(
//        @Named(io.loperilla.onboarding_domain.model.database.Constants.SHOPPINGLIST) databaseReference: DatabaseReference
//    ): CustomReference.SHOPPING_LIST_REFERENCE = CustomReference.SHOPPING_LIST_REFERENCE(databaseReference)
//
//    @Named(io.loperilla.onboarding_domain.model.database.Constants.SHOPPINGLIST)
//    @Singleton
//    @Provides
//    fun providesShoppingListDatabase(): DatabaseReference =
//        Firebase.database.reference.child(io.loperilla.onboarding_domain.model.database.Constants.SHOPPINGLIST)
//
//    @Singleton
//    @Provides
//    fun providesItemShoppingReference(
//        @Named(io.loperilla.onboarding_domain.model.database.Constants.ITEMS) databaseReference: CollectionReference,
//        @Named(io.loperilla.onboarding_domain.model.database.Constants.IMAGES) storageReference: StorageReference
//    ): CustomReference.SHOPPING_ITEM_LIST_COLLECTION =
//        CustomReference.SHOPPING_ITEM_LIST_COLLECTION(databaseReference, storageReference)
//
//    @Singleton
//    @Provides
//    fun providesShoppingCartDatabase(
//        dataStore: UserDataStore,
//        shoppingRef: CustomReference.SHOPPING_LIST_REFERENCE,
//    ) = ShoppingCartListFirebaseDatabase(dataStore, shoppingRef)
//
//    @Named(io.loperilla.onboarding_domain.model.database.Constants.ITEMS)
//    @Singleton
//    @Provides
//    fun providesItemsShoppingListCollection(): CollectionReference =
//        Firebase.firestore.collection(io.loperilla.onboarding_domain.model.database.Constants.ITEMS)
//
//    @Singleton
//    @Provides
//    fun providesItemShoppingDatabase(
//        itemShoppingReference: CustomReference.SHOPPING_ITEM_LIST_COLLECTION
//    ) = ShoppingItemListFirebaseDatabase(itemShoppingReference)
//
//    @Named(io.loperilla.onboarding_domain.model.database.Constants.IMAGES)
//    @Singleton
//    @Provides
//    fun provideFirebaseStorage() = Firebase.storage.reference.child(io.loperilla.onboarding_domain.model.database.Constants.IMAGES)
}