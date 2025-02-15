package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.loperilla.designsystem.composables.Screen
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.snackbar.SnackbarController
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Screen {
                val navigator : Navigator by inject()
                val snackbarManager : SnackbarController by inject()

                val viewModel: MainActivityViewModel = koinViewModel()
                val uiState: SplashState by viewModel.stateFlow.collectAsStateWithLifecycle()

                splashScreen.setKeepOnScreenCondition {
                    when (uiState) {
                        SplashState.Loading -> true
                        else -> {

                            false
                        }
                    }
                }
                AppNavigation(
                    navigator,
                    snackbarManager
                )
            }
        }
    }
}
