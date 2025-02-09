package io.loperilla.presentation.di

import io.loperilla.presentation.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 9/2/25 at 16:54
 * All rights reserved 2025
 */

val registerViewModelModule = module {
    viewModelOf(::RegisterViewModel)
}