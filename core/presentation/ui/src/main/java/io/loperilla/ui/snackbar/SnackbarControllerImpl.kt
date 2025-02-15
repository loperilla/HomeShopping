package io.loperilla.ui.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.snackbar
 * Created By Manuel Lopera on 8/2/25 at 17:16
 * All rights reserved 2025
 */
class SnackbarControllerImpl : SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    override val events: Flow<SnackbarEvent>
        get() = _events.receiveAsFlow()

    override suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}