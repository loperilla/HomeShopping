package io.loperilla.domain.di

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.di
 * Created By Manuel Lopera on 26/4/25 at 11:09
 * All rights reserved 2025
 */

import io.loperilla.domain.AddProductUseCase
import io.loperilla.domain.GetAllProductsUseCase
import io.loperilla.domain.RemoveProductUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val productsDomainModule: Module = module {
    factoryOf(::GetAllProductsUseCase)
    factoryOf(::RemoveProductUseCase)
    factoryOf(::AddProductUseCase)
}