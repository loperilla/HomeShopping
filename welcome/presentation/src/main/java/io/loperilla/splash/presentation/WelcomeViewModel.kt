package io.loperilla.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.loperilla.ui.navigator.Navigator
import io.loperilla.ui.navigator.routes.Destination
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.splash.presentation
 * Created By Manuel Lopera on 9/2/25 at 12:42
 * All rights reserved 2025
 */
class WelcomeViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    fun onEvent(newEvent: WelcomeEvent) = viewModelScope.launch {
        when (newEvent) {
            WelcomeEvent.NavigateToLogin -> navigator.navigateTo(Destination.Login)
            WelcomeEvent.NavigateToRegister -> navigator.navigateTo(Destination.Register)
        }
    }
}