package io.loperilla.ui.di

import io.loperilla.ui.navigator.DefaultNavigator
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import io.loperilla.ui.snackbar.SnackbarControllerImpl
import org.koin.dsl.module

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.di
 * Created By Manuel Lopera on 2/2/25 at 18:24
 * All rights reserved 2025
 */

val uiModule = module {
    single<Navigator> { DefaultNavigator() }
    single<SnackbarController> { SnackbarControllerImpl() }
}