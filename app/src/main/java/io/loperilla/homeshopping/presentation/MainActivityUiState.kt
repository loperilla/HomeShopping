package io.loperilla.homeshopping.presentation

/*****
 * Project: HomeShopping
 * From: io.loperilla.homeshopping.presentation
 * Created By Manuel Lopera on 29/11/25 at 10:28
 * All rights reserved 2025
 */
sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val goToWelcome: Boolean) : MainActivityUiState
}