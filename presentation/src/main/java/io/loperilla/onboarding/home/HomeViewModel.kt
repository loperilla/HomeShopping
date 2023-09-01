package io.loperilla.onboarding.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.loperilla.model.database.ShoppingItem
import io.loperilla.model.database.result.ReadDatabaseResult
import io.loperilla.onboarding_domain.usecase.home.HomeUseCase
import io.loperilla.onboarding_domain.usecase.home.LogoutUseCase
import kotlinx.coroutines.Dispatchers
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
    private val logoutUseCase: LogoutUseCase,
    private val homeUseCase: HomeUseCase
) : ViewModel() {
    private var _showLogoutDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog

    private var _logoutFinish: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val logoutFinish: StateFlow<Boolean> = _logoutFinish

    private var _shoppingBuyList: MutableStateFlow<List<ShoppingItem>> = MutableStateFlow(emptyList())
    val shoppingBuyList: StateFlow<List<ShoppingItem>> = _shoppingBuyList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.getAllShopping().collect { result ->
                when (result) {
                    is ReadDatabaseResult.FAIL -> {
                        _logoutFinish.value = true
                    }

                    is ReadDatabaseResult.SUCCESS -> {
                        _shoppingBuyList.value = result.result
                    }
                }
            }
        }
    }

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
