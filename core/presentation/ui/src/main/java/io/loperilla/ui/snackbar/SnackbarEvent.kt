package io.loperilla.ui.snackbar

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.snackbar
 * Created By Manuel Lopera on 8/2/25 at 17:15
 * All rights reserved 2025
 */
data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

data class SnackbarEvent(
    val message: String,
    val actionLabel: SnackbarAction? = null,
)


