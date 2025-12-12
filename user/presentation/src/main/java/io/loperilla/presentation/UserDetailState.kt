package io.loperilla.presentation

import io.loperilla.domain.model.auth.User

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/3/25 at 17:35
 * All rights reserved 2025
 */
data class UserDetailState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val newUserName: String = "",
) {
    val validForm: Boolean
        get() = newUserName.isNotBlank() && user?.name != newUserName
}
