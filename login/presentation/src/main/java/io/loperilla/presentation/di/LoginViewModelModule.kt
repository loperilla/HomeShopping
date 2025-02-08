package io.loperilla.presentation.di

import io.loperilla.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 2/2/25 at 18:37
 * All rights reserved 2025
 */

val loginViewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
}