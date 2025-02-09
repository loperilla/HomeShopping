package io.loperilla.splash.presentation.di

import io.loperilla.splash.presentation.WelcomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.splash.presentation.di
 * Created By Manuel Lopera on 9/2/25 at 12:46
 * All rights reserved 2025
 */

val welcomeViewModelModule = module {
    viewModelOf(::WelcomeViewModel)
}