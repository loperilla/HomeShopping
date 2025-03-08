package io.loperilla.domain.usecase.di

import io.loperilla.domain.usecase.GetLastShoppingListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.usecase.di
 * Created By Manuel Lopera on 22/2/25 at 11:23
 * All rights reserved 2025
 */

val homeUseCaseModule = module {
    singleOf(::GetLastShoppingListUseCase)
}