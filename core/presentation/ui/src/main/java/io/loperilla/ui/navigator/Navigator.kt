package io.loperilla.ui.navigator

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
    val navigationActions: Flow<NavigationAction>

    suspend fun navigateTo(
        destination: Destination,
    )

    suspend fun navigateUp()
    suspend fun navigateUpTo(
        destination: Destination
    )
    suspend fun navigateToAndClearStack(route: Destination)
}

class DefaultNavigator : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigateTo(
        destination: Destination,
    ) = _navigationActions.send(
        NavigationAction.Navigate(
            route = destination,
        )
    )

    override suspend fun navigateUp() =
        _navigationActions.send(NavigationAction.NavigateUp)

    override suspend fun navigateUpTo(destination: Destination) = _navigationActions.send(
        NavigationAction.NavigateUpTo(
            route = destination
        )
    )

    override suspend fun navigateToAndClearStack(destination: Destination) = _navigationActions.send(
        NavigationAction.NavigateAndClearStack(
            destination = destination
        )
    )
}