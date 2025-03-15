package io.loperilla.presentation.di

import io.loperilla.presentation.UserDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation.di
 * Created By Manuel Lopera on 9/3/25 at 17:36
 * All rights reserved 2025
 */

val userDetailViewModelModule = module {
    viewModelOf(::UserDetailViewModel)
}