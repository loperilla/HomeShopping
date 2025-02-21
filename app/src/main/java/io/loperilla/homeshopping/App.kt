package io.loperilla.homeshopping

import android.app.Application
import com.google.firebase.FirebaseApp
import io.loperilla.data.di.dataModule
import io.loperilla.data.di.loginDataModule
import io.loperilla.data.di.registerDataModule
import io.loperilla.domain.di.coreUseCaseModule
import io.loperilla.homeshopping.di.mainActivityModule
import io.loperilla.presentation.di.homeViewModelModule
import io.loperilla.presentation.di.loginViewModelModule
import io.loperilla.presentation.di.registerViewModelModule
import io.loperilla.splash.presentation.di.welcomeViewModelModule
import io.loperilla.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(mainActivityModule)
            modules(
                welcomeViewModelModule
            )
            modules(getCoreModules())
            modules(getLoginModules())
            modules(getRegisterModules())
            modules(getHomeModules())
        }
    }

    private fun getCoreModules(): List<Module> = listOf(dataModule, coreUseCaseModule, uiModule)
    private fun getLoginModules(): List<Module> = listOf(loginDataModule, loginViewModelModule)
    private fun getRegisterModules(): List<Module> =
        listOf(registerDataModule, registerViewModelModule)

    private fun getHomeModules(): List<Module> = listOf(homeViewModelModule)
}
