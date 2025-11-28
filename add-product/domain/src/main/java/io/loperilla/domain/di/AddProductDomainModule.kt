package io.loperilla.domain.di

import io.loperilla.domain.AddProductUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.domain.di
 * Created By Manuel Lopera on 28/11/25 at 16:51
 * All rights reserved 2025
 */

val addProductDomainModule = module {
    factoryOf(::AddProductUseCase)
}