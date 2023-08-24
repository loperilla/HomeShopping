package io.loperilla.onboarding.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.onboarding_domain.usecase.home.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 24/8/23 at 20:19
 * All rights reserved 2023
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private var _showLogoutDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog

    private var _logoutFinish: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val logoutFinish: StateFlow<Boolean> = _logoutFinish

    fun showLogoutDialog() {
        viewModelScope.launch {
            _showLogoutDialog.value = true
        }
    }

    fun hideLogoutDialog() {
        viewModelScope.launch {
            _showLogoutDialog.value = false
        }
    }

    fun doLogout() {
        viewModelScope.launch {
            logoutUseCase()
            _logoutFinish.value = true
        }
    }
}
