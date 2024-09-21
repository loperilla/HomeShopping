package io.loperilla.homeshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.loperilla.core_ui.Screen
import io.loperilla.onboarding.AppNavigation
import io.loperilla.onboarding.navigator.Navigator
import io.loperilla.onboarding_domain.model.SplashUIState
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping
 * Created By Manuel Lopera on 21/8/23 at 17:07
 * All rights reserved 2023
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            Screen {
                val viewModel: MainActivityViewModel = hiltViewModel()
                val uiState: SplashUIState by viewModel.splashUiState.collectAsStateWithLifecycle()

                splashScreen.setKeepOnScreenCondition {
                    when (uiState) {
                        SplashUIState.Loading -> true
                        else -> false
                    }
                }
                AppNavigation(
                    navigator
                )
            }
        }
    }
}
