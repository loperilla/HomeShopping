package io.loperilla.onboarding_domain.di.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import io.loperilla.onboarding_domain.usecase.DoLoginUseCase

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.di.login
 * Created By Manuel Lopera on 23/4/23 at 12:15
 * All rights reserved 2023
 */

@Module
@InstallIn(SingletonComponent::class)
object DomainDependencyInjector {
    @Provides
    fun providesDoLoginUseCase(
        firebaseAuth: FirebaseAuthRepository
    ): DoLoginUseCase = DoLoginUseCase(firebaseAuth)

//    @Provides
//    fun providesHomeUseCase(
//        shoppingListRepository: ShoppingListRepository
//    ): HomeUseCase = HomeUseCase(shoppingListRepository)
}
