package io.loperilla.onboarding_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.loperilla.data.firebase.auth.FirebaseAuthRepository
import io.loperilla.data.firebase.database.ShoppingRepository
import io.loperilla.onboarding_domain.usecase.auth.DoLoginUseCase
import io.loperilla.onboarding_domain.usecase.home.HomeUseCase
import io.loperilla.onboarding_domain.usecase.home.LogoutUseCase

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding_domain.di.login
 * Created By Manuel Lopera on 23/4/23 at 12:15
 * All rights reserved 2023
 */

@Module
@InstallIn(ViewModelComponent::class)
object DomainDependencyInjector {
    @Provides
    fun providesDoLoginUseCase(
        firebaseAuth: FirebaseAuthRepository
    ): DoLoginUseCase = DoLoginUseCase(firebaseAuth)

    @Provides
    fun providesLogoutUseCase(
        firebaseAuth: FirebaseAuthRepository
    ): LogoutUseCase = LogoutUseCase(firebaseAuth)

    @Provides
    fun providesHomeUseCase(
        shoppingListRepository: ShoppingRepository
    ): HomeUseCase = HomeUseCase(shoppingListRepository)
}
