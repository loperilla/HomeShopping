package io.loperilla.ui.navigator

import androidx.navigation.NavOptionsBuilder
import io.loperilla.ui.navigator.routes.Destination
import io.loperilla.ui.navigator.routes.NavigationAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.navigator
 * Created By Manuel Lopera on 2/2/25 at 17:19
 * All rights reserved 2025
 */
interface Navigator {
    var startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    fun setUpStartDestination(
        startDestination: Destination
    )

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

class DefaultNavigator : Navigator {
    override var startDestination: Destination = Destination.AuthGraph
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override fun setUpStartDestination(startDestination: Destination) {
        this.startDestination = startDestination
    }

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            NavigationAction.Navigate(
                route = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}