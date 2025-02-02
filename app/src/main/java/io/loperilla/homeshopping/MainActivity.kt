package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.loperilla.onboarding.navigator.Navigator

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */

class MainActivity : ComponentActivity() {
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            io.loperilla.ui.Screen {
//                val viewModel: MainActivityViewModel = koinViewModel()
//                val uiState: SplashUIState by viewModel.splashUiState.collectAsStateWithLifecycle()
//
//                splashScreen.setKeepOnScreenCondition {
//                    when (uiState) {
//                        SplashUIState.Loading -> true
//                        else -> false
//                    }
//                }
//                AppNavigation(
//                    navigator
//                )
            }
        }
    }
}
