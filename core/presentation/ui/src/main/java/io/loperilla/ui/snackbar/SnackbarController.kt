package io.loperilla.ui.snackbar

import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.snackbar
 * Created By Manuel Lopera on 8/2/25 at 17:14
 * All rights reserved 2025
 */
interface SnackbarController {
    val events : Flow<SnackbarEvent>
    suspend fun sendEvent(event: SnackbarEvent)
}