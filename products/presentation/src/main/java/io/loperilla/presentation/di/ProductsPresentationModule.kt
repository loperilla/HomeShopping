package io.loperilla.presentation.di

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 26/4/25 at 11:08
 * All rights reserved 2025
 */

import io.loperilla.presentation.ProductsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productsPresentationModule: Module = module {
    viewModelOf(::ProductsViewModel)
}