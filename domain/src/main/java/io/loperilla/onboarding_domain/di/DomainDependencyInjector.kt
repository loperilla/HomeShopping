package io.loperilla.onboarding_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.loperilla.data.firebase.database.ShoppingRepository
import io.loperilla.data.repository.QueryRepository
import io.loperilla.data.repository.auth.AuthRepository
import io.loperilla.data.repository.database.CommerceRepository
import io.loperilla.onboarding_domain.usecase.auth.DoLoginUseCase
import io.loperilla.onboarding_domain.usecase.auth.LogoutUseCase
import io.loperilla.onboarding_domain.usecase.auth.RegisterUseCase
import io.loperilla.onboarding_domain.usecase.commerce.GetCommerceListUseCase
import io.loperilla.onboarding_domain.usecase.home.HomeUseCase
import io.loperilla.onboarding_domain.usecase.shoppingcart.QueryUseCase

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
        firebaseAuth: AuthRepository
    ): DoLoginUseCase = DoLoginUseCase(firebaseAuth)

    @Provides
    fun providesLogoutUseCase(
        firebaseAuth: AuthRepository
    ): LogoutUseCase = LogoutUseCase(firebaseAuth)

    @Provides
    fun providesRegisterUseCase(
        firebaseAuth: AuthRepository
    ): RegisterUseCase = RegisterUseCase(firebaseAuth)

    @Provides
    fun providesHomeUseCase(
        shoppingListRepository: ShoppingRepository
    ): HomeUseCase = HomeUseCase(shoppingListRepository)

    @Provides
    fun providesQueryUseCase(
        queryRepository: QueryRepository
    ): QueryUseCase = QueryUseCase(queryRepository)

    @Provides
    fun providesGetCommerceListUseCase(
        commerceRepository: CommerceRepository
    ): GetCommerceListUseCase= GetCommerceListUseCase(commerceRepository)
}
